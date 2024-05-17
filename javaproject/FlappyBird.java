import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;



public class FlappyBird extends JFrame implements ActionListener, KeyListener, ComponentListener {
    private int WIDTH = 800;
    private int HEIGHT = 600;
    private final int GRAVITY = 1;
    private final int JUMP_FORCE = -10;
    private final int BALL_SIZE = 25;
    private final int PIPE_WIDTH = 50;
    private final int PIPE_HEIGHT = 205; // Increased by 5 pixels
    private final int PIPE_GAP = 200;
    private final int PIPE_SPEED = 5;

    private Timer timer;
    private int birdY, velocity, score;
    private boolean isJumping, isGameOver, isGameStarted;
    private int pipeX, pipeY;

    private int numStars = 50;
    private int[] starX;
    private int[] starY;

    public static void dbSet(String sql) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "mysql123");
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        connection.close();
    }
    public static ResultSet dbGet(String sql) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "mysql123");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
      }

    public FlappyBird() {
        setTitle("Flappy Bird");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

        birdY = HEIGHT / 2;
        velocity = 0;
        score = 0;
        isJumping = false;
        isGameOver = false;
        isGameStarted = false;
        pipeX = WIDTH;
        pipeY = 0;

        addKeyListener(this);
        addComponentListener(this);

        timer = new Timer(20, this);
        timer.setInitialDelay(0);
        timer.start();

        initializeStars();
    }

    private void initializeStars() {
        starX = new int[numStars];
        starY = new int[numStars];

        for (int i = 0; i < numStars; i++) {
            starX[i] = (int) (Math.random() * WIDTH);
            starY[i] = (int) (Math.random() * HEIGHT);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        // Night sky background with stars
        g.setColor(new Color(0, 0, 51));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Draw stars
        g.setColor(Color.WHITE);
        for (int i = 0; i < numStars; i++) {
            g.fillRect(starX[i], starY[i], 2, 2);
        }

        // Draw pipes
        g.setColor(Color.WHITE);
        g.fillRect(pipeX, pipeY, PIPE_WIDTH, PIPE_HEIGHT);
        g.fillRect(pipeX, pipeY + PIPE_HEIGHT + PIPE_GAP, PIPE_WIDTH, HEIGHT - pipeY - PIPE_HEIGHT - PIPE_GAP - 150);

        // Draw moon surface
        g.setColor(new Color(128, 128, 128));
        g.fillRect(0, HEIGHT - 150, WIDTH, 150);

        // Draw sun (ball)
        g.setColor(new Color(153, 102, 0));
        g.fillOval(WIDTH / 2 - BALL_SIZE / 2, birdY - BALL_SIZE / 2, BALL_SIZE, BALL_SIZE);

        // Draw score at the bottom on the moon surface
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Score: " + score, 20, HEIGHT - 70);

        // Draw game over message at the bottom on the moon surface
        if (isGameOver) {
        try{
            dbSet("insert into scores(score) values("+ score +");");
        }catch(Exception e){
            g.drawString("Error inserting into database", WIDTH / 2 - 180, HEIGHT / 2);
        }
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.drawString("Game Over!", WIDTH / 2 - 150, HEIGHT / 2 - 100);
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            g.drawString("Your Score: " + score, WIDTH / 2 - 100, HEIGHT / 2 - 50);
            try{
            ResultSet MaxScore = dbGet("select max(score) as max_score from scores");
            MaxScore.next();
            g.drawString("High Score: " + MaxScore.getString("max_score"), WIDTH / 2 - 100, HEIGHT / 2 - 20);
            }catch(Exception e){
                g.drawString("Error inserting into database", WIDTH / 2 - 180, HEIGHT / 2);
            }
            g.drawString("Press Enter to Play Again", WIDTH / 2 - 180, HEIGHT / 2+20);
        }

        // Draw initial message
        if (!isGameStarted) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            g.drawString("Press Space to Start", WIDTH / 2 - 180, HEIGHT / 2 - 12);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (isGameStarted && !isGameOver) {
            birdY += velocity;
            velocity += GRAVITY;

            pipeX -= PIPE_SPEED;
            if (pipeX < -PIPE_WIDTH) {
                pipeX = WIDTH;
                pipeY = (int) (Math.random() * (HEIGHT - 350)) - 50;
            }

            // Increment score if the bird has passed through the pillars
            if (pipeX + PIPE_WIDTH == WIDTH / 2) {
                score++;
            }

            if (birdY + BALL_SIZE / 2 >= HEIGHT - 150 || birdY - BALL_SIZE / 2 <= 0 || collision()) {
                isGameOver = true;
                timer.stop();
                //database
                
            }

            if (isJumping) {
                velocity = JUMP_FORCE;
                isJumping = false;
                updateStars();
            }

            repaint();
        }
    }

    private boolean collision() {
        return ((birdY + BALL_SIZE / 2 >= pipeY && birdY - BALL_SIZE / 2 <= pipeY + PIPE_HEIGHT) &&
                        (pipeX <= WIDTH / 2 && pipeX + PIPE_WIDTH >= WIDTH / 2));
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!isGameOver && key == KeyEvent.VK_SPACE) {
            if (!isGameStarted) {
                isGameStarted = true;
            }
            velocity = JUMP_FORCE;
            isJumping = true;
        } else if (isGameOver && key == KeyEvent.VK_ENTER) {
            restartGame();
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    private void restartGame() {
        birdY = HEIGHT / 2;
        velocity = 0;
        score = 0;
        isJumping = false;
        isGameOver = false;
        isGameStarted = false;
        pipeX = WIDTH;
        pipeY = 0;
        timer.restart();
        initializeStars();
        repaint();
    }

    private void updateStars() {
        // Move stars randomly when the ball jumps
        for (int i = 0; i < numStars; i++) {
            starX[i] += (int) (Math.random() * 11) - 5; // Move horizontally by -5 to +5 pixels
            starY[i] += (int) (Math.random() * 11) - 5; // Move vertically by -5 to +5 pixels

            // Keep stars within bounds
            starX[i] = (starX[i] + WIDTH) % WIDTH;
            starY[i] = (starY[i] + HEIGHT) % HEIGHT;
        }
    }

    public void componentResized(ComponentEvent e) {
        int newWidth = getWidth();
        int newHeight = getHeight();
        if (newWidth != WIDTH || newHeight != HEIGHT) {
            // Update WIDTH and HEIGHT
            WIDTH = newWidth;
            HEIGHT = newHeight;
            // Adjust the position of elements accordingly
            birdY = HEIGHT / 2;
            pipeX = WIDTH;
            initializeStars();
            // Repaint
            repaint();
        }
    }

    public void componentMoved(ComponentEvent e) {}

    public void componentShown(ComponentEvent e) {}

    public void componentHidden(ComponentEvent e) {}

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            FlappyBird game = new FlappyBird();
            game.setVisible(true);
        });

        
    }
} 