import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Main {

    public static void main(String[] args) {
        // Initial frame setup
        JFrame frame = new JFrame("OS Chooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setLayout(new BorderLayout());

        // Create the initial page
        createInitialPage(frame);

        // Display the frame
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    // Method to create the initial page with OS selection buttons
    private static void createInitialPage(JFrame frame) {
        frame.getContentPane().removeAll(); // Clear previous content

        JLabel titleLabel = new JLabel("Choose Your OS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(150, 50); // Adjustable button size

        // Button icons (adjust path if needed)
        ImageIcon windowsIcon = resizeIcon(new ImageIcon("src/windows.png"), 20, 20);
        ImageIcon macIcon = resizeIcon(new ImageIcon("src/mac.png"), 20, 20);
        ImageIcon linuxIcon = resizeIcon(new ImageIcon("src/linux.png"), 20, 20);

        // Create OS buttons
        JButton windowsButton = new JButton("Windows", windowsIcon);
        windowsButton.setMaximumSize(buttonSize);
        windowsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        windowsButton.addActionListener(e -> createDownloadPage(frame, "src/install-current-user.vbs"));

        JButton macButton = new JButton("Mac", macIcon);
        macButton.setMaximumSize(buttonSize);
        macButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        macButton.addActionListener(e -> createDownloadPage(frame, "src/install.sh"));

        JButton linuxButton = new JButton("Linux", linuxIcon);
        linuxButton.setMaximumSize(buttonSize);
        linuxButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        linuxButton.addActionListener(e -> createDownloadPage(frame, "src/install.sh"));

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(windowsButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(macButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(linuxButton);

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    // Method to create the download page
    private static void createDownloadPage(JFrame frame, String s) {
        frame.getContentPane().removeAll(); // Clear previous content

        JLabel downloadLabel = new JLabel("Download Page", SwingConstants.CENTER);
        downloadLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(downloadLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // "Download Now" link (using JButton to simulate link)
        JButton downloadLink = new JButton("Download Now");
        downloadLink.setBorderPainted(false);
        downloadLink.setContentAreaFilled(false);
        downloadLink.setForeground(Color.BLUE.darker());
        downloadLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        downloadLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                downloadFile("src/install.sh");
            }
        });

        // Drop-down menu
        String[] options = {"Version 1.0", "Version 2.0", "Version 3.0"};
        JComboBox<String> dropdownMenu = new JComboBox<>(options);
        dropdownMenu.setMaximumSize(new Dimension(200, 30)); // Adjust size
        dropdownMenu.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components to content panel
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(downloadLink);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(dropdownMenu);

        // JEditorPane to display a web page or HTML content
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false); // Make it non-editable

        // Load a web page
        try {
            URL url = new URL("https://www.google.com"); // Change to your desired URL
            editorPane.setPage(url);
        } catch (IOException e) {
            editorPane.setText("Failed to load web page: " + e.getMessage());
        }

        // Add the editor pane to a scroll pane for scrolling
        JScrollPane editorScrollPane = new JScrollPane(editorPane);
        editorScrollPane.setPreferredSize(new Dimension(600, 200)); // Adjust size as needed
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(editorScrollPane);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> createInitialPage(frame));
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(backButton);

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    // Method to resize an icon
    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Method to simulate a file download by copying a file
    private static void downloadFile(String fileName) {
        try {
            // Assume file exists in the project directory
            File sourceFile = new File(fileName);
            if (!sourceFile.exists()) {
                JOptionPane.showMessageDialog(null, "File not found: " + fileName);
                return;
            }

            // Choose destination
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Download");
            fileChooser.setSelectedFile(new File(fileChooser.getCurrentDirectory(), sourceFile.getName()));

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File destinationFile = fileChooser.getSelectedFile();
                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                JOptionPane.showMessageDialog(null, "Download complete!");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Download failed: " + ex.getMessage());
        }
    }
}