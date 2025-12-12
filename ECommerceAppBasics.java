import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Queue;
class Order {
    private final String orderId;
    private final List<CartItem> items;
    private final double totalAmount;

    public Order(List<CartItem> items, double totalAmount) {
        this.orderId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.items = new ArrayList<>(items); // Create a copy of the items
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<CartItem> getItems() {
    return items;
}

    @Override
    public String toString() {
        return "Order #" + orderId + " (" + items.size() + " items) - Total: ₹" + String.format("%.2f", totalAmount);
    }
}

class Product {
    private final String name;
    private final double price;
    private final String description;
    private final String imagePath;

    public Product(String name, double price, String description, String imagePath) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    // Simplified price formatting
    public String getFormattedPrice() {
        return "₹" + String.format("%.2f", price);
    }
}

class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }


    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void decrementQuantity() {
        if (quantity > 1)
            quantity--;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

public class ECommerceAppBasics extends JFrame {
    private  final JTextField searchField;
    private final JPanel productPanel;
    private  final ArrayList<Product> allProducts = new ArrayList<>();
    private  final ArrayList<CartItem> cartItems = new ArrayList<>();
    private  final JButton cartBtn;

    // Use a simple LinkedList as a Queue
    private final Queue<Order> orderQueue = new LinkedList<>();
    private final List<Order> completedOrders = new ArrayList<>();

    public ECommerceAppBasics() {
        setTitle("JK - Java Edition (Basic)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Header Panel
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(19, 25, 33));
        header.setPreferredSize(new Dimension(1100, 70));

        JLabel logo = new JLabel("JK.in  ");
        logo.setForeground(new Color(255, 153, 0));
        logo.setFont(new Font("Arial", Font.BOLD, 28));
        logo.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 0));

        searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(255, 153, 0));
        searchBtn.addActionListener(e -> filterProducts());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 10, 80));

        cartBtn = new JButton();
        cartBtn.setBackground(new Color(255, 153, 0));
        cartBtn.setFont(new Font("Arial", Font.BOLD, 14));
        cartBtn.addActionListener(e -> showCart());
        updateCartCount();

        JButton queueStatusBtn = new JButton("Processing Queue");
        queueStatusBtn.setBackground(Color.WHITE);
        queueStatusBtn.addActionListener(e -> showOrderQueueStatus());

        JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        eastPanel.setOpaque(false);
        eastPanel.add(queueStatusBtn);
        eastPanel.add(cartBtn);

        header.add(logo, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.CENTER);
        header.add(eastPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Product Grid
        productPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        productPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        add(scrollPane, BorderLayout.CENTER);

        loadProducts();
        displayProducts(allProducts);

        setVisible(true);
    }

    private void updateCartCount() {
        // Simplified using a basic for-each loop
        int totalItems = 0;
        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }
        cartBtn.setText("CART(" + totalItems + ")");
    }

    private void loadProducts() {
        // Same product loading logic
         allProducts.add(new Product("Apple iPhone 15", 79999, "The latest iPhone with A17 chip and Dynamic Island.", "Images/Iphone 15.png"));
        allProducts.add(new Product("Samsung Galaxy S24", 69999, "Flagship Samsung smartphone with AMOLED 120Hz display.", "Images/S24.png"));
        allProducts.add(new Product("Sony WH-1000XM5", 29990, "Industry-leading noise cancellation headphones.", "Images/Sony WH.png"));
        allProducts.add(new Product("Nike Air Max 270", 12499, "Stylish sports shoes with great comfort and cushioning.", "Images/Nike Air.png"));
        allProducts.add(new Product("OnePlus Watch 2", 24999, "Premium smartwatch with long battery life.", "Images/OnePlus Watch 2.png"));
        allProducts.add(new Product("Dell Inspiron 15", 54990, "Powerful laptop for work and entertainment.", "Images/Dell Inspiron 15.png"));
        allProducts.add(new Product("Canon EOS 200D", 58999, "Perfect DSLR for beginners and creators.", "Images/Canon EOS 200D.png"));
        allProducts.add(new Product("Apple AirPods Pro", 26999, "Wireless earbuds with active noise cancellation.", "Images/Apple AirPods Pro.png"));
        allProducts.add(new Product("ASUS ROG Gaming Laptop", 109999, "High-performance gaming beast with RTX GPU.", "Images/ASUS ROG Gaming Laptop.png"));
    }

    private void displayProducts(List<Product> products) {
        productPanel.removeAll();
        for (Product product : products) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            card.setBackground(Color.WHITE);

            JLabel imageLabel = new JLabel();
            try {
                java.net.URL imgURL = getClass().getResource(product.getImagePath());
                if (imgURL != null) {
                    ImageIcon originalIcon = new ImageIcon(imgURL);
                    Image resizedImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(resizedImage));
                } else {
                    imageLabel.setText("No Image");
                }
            } catch (Exception e) {
                imageLabel.setText("Error");
            }
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel nameLbl = new JLabel(product.getName());
            nameLbl.setFont(new Font("Arial", Font.BOLD, 15));
            nameLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel priceLbl = new JLabel(product.getFormattedPrice());
            priceLbl.setFont(new Font("Arial", Font.PLAIN, 14));
            priceLbl.setForeground(new Color(0, 128, 0));
            priceLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton addBtn = new JButton("Add to Cart");
            addBtn.setBackground(new Color(255, 153, 0));
            addBtn.setFont(new Font("Arial", Font.BOLD, 12));
            addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            addBtn.addActionListener(e -> addToCart(product));

            card.add(imageLabel);
            card.add(Box.createRigidArea(new Dimension(0, 10)));
            card.add(nameLbl);
            card.add(Box.createRigidArea(new Dimension(0, 5)));
            card.add(priceLbl);
            card.add(Box.createRigidArea(new Dimension(0, 10)));
            card.add(addBtn);

            productPanel.add(card);
        }
        productPanel.revalidate();
        productPanel.repaint();
    }

    private void filterProducts() {
        String query = searchField.getText().toLowerCase();
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(query)) {
                filteredProducts.add(product);
            }
        }
        displayProducts(filteredProducts);
    }

    private void addToCart(Product product) {
        // Simplified with a basic for-each loop
        boolean itemExistsInCart = false;
        for (CartItem item : cartItems) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.incrementQuantity();
                itemExistsInCart = true;
                break;
            }
        }

        if (!itemExistsInCart) {
            cartItems.add(new CartItem(product));
        }
        updateCartCount();
    }

    private void showCart() {
        // Simplified calculation with a for-each loop
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getTotalPrice();
        }

        StringBuilder cartDetails = new StringBuilder();
        if (cartItems.isEmpty()) {
            cartDetails.append("Your cart is empty.");
        } else {
            for (CartItem item : cartItems) {
                cartDetails.append(item.getProduct().getName())
                        .append(" x").append(item.getQuantity())
                        .append(" - ₹").append(String.format("%.2f", item.getTotalPrice()))
                        .append("\n");
            }
            cartDetails.append("\nTotal: ₹").append(String.format("%.2f", totalAmount));
        }

        if (!cartItems.isEmpty()) {
            int response = JOptionPane.showConfirmDialog(this, cartDetails.toString(), "Cart",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (response == JOptionPane.OK_OPTION) {
                placeOrder(totalAmount);
            }
        } else {
            JOptionPane.showMessageDialog(this, cartDetails.toString(), "Cart", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void placeOrder(double totalAmount) {
        Order newOrder = new Order(cartItems, totalAmount);
        orderQueue.offer(newOrder); // Add order to the queue
        JOptionPane.showMessageDialog(this,
                "Order placed successfully!\nOrder ID: " + newOrder.getOrderId(),
                "Order Placed", JOptionPane.INFORMATION_MESSAGE);
        cartItems.clear();
        updateCartCount();
    }

    private void processNextOrderFromQueue() {
        Order orderToProcess = orderQueue.poll(); // Get and remove the order from the queue
        if (orderToProcess != null) {
            // Simulate processing in a new, separate thread to avoid freezing the UI
            new Thread(() -> {
                try {
                    // Simulate a delay for processing
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }

                completedOrders.add(orderToProcess);

                // Update the UI on the Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this,
                            "Order Processing Complete!\nOrder ID: " + orderToProcess.getOrderId(),
                            "Order Complete", JOptionPane.INFORMATION_MESSAGE);
                    // Re-display the queue status after processing is done
                    showOrderQueueStatus();
                });
            }).start();
        }
    }

    private void showOrderQueueStatus() {
        JDialog queueDialog = new JDialog(this, "Order Queue Status", true);
        queueDialog.setLayout(new BorderLayout(10, 10));
        queueDialog.setSize(500, 400);
        queueDialog.setLocationRelativeTo(this);

        JTextArea queueDisplay = new JTextArea(15, 30);
        queueDisplay.setEditable(false);
        queueDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));

        if (orderQueue.isEmpty()) {
            queueDisplay.setText("The order processing queue is empty.");
        } else {
            StringBuilder sb = new StringBuilder("Orders waiting to be processed:\n\n");
            int i = 1;
            for (Order order : orderQueue) {
                String status = (i == 1) ? " (Next to be processed)" : "";
                sb.append(i).append(". ").append(order.toString()).append(status).append("\n");
                i++;
            }
            queueDisplay.setText(sb.toString());
        }

        queueDialog.add(new JScrollPane(queueDisplay), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JButton proceedBtn = new JButton("Process Next Order");
        proceedBtn.setEnabled(!orderQueue.isEmpty()); // Button is disabled if queue is empty
        proceedBtn.addActionListener(e -> {
            queueDialog.dispose();
            processNextOrderFromQueue();
        });

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> queueDialog.dispose());

        controlPanel.add(proceedBtn);
        controlPanel.add(closeBtn);
        queueDialog.add(controlPanel, BorderLayout.SOUTH);
        queueDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ECommerceAppBasics::new);
    }
}