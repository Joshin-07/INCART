# 🛒 E-Commerce App Basics – Java Swing Cart & Order Queue Manager

A simple yet effective Java Swing desktop application that simulates an e-commerce shopping flow adding items to a cart, creating orders, and processing them using a **Queue**.
This project demonstrates practical use of **Data Structures (LinkedList Queue)** + **Swing GUI** + **OOP**.

---

## ✨ Features

### 🛍️ Add Items to Cart

* Choose a product from a dropdown list.
* Select quantity using a spinner.
* Items are added dynamically to the cart.

### 🛒 View Cart

* Displays product name, quantity, and calculated price.
* Shows **grand total**.
* Automatically updates when items are added.

### 📦 Place Order

* Converts cart contents into an **Order object**.
* Pushes the order into an **Order Queue (FIFO)**.
* Clears the cart after placing the order.

### 🚚 Process Next Order

* Processes orders in FIFO order using `queue.poll()`.
* Displays order details inside a dialog box.

### 📃 View Order Queue

* Shows all pending orders waiting to be processed.
* Includes order ID, items, and total amount.

### 🖥️ Modern & Simple UI

Built with Swing components:
`JFrame`, `JButton`, `JTextArea`, `JComboBox`, `JSpinner`, `JDialog`.

---

## ⚙️ Tech Stack

| Layer            | Tools                    |
| ---------------- | ------------------------ |
| **Language**     | Java                     |
| **Frontend**     | Java Swing               |
| **DS Logic**     | Queue (LinkedList), OOP  |
| **Architecture** | Event-Driven Programming |

---

## 📂 Project Structure

```
ECommerceAppBasics.java
|
|–– CartItem           // Model class for items in cart
|–– Order              // Model class for Orders
|–– LinkedList Queue   // Used for storing pending orders
|–– Swing UI           // Full GUI implementation
```

---

## 🧭 How to Run

### 1️⃣ Clone the repo

```bash
git clone https://github.com/Joshin-07/ECommerceAppBasics.git
```

### 2️⃣ Navigate to the folder

```bash
cd ECommerceAppBasics/src
```

### 3️⃣ Compile the program

```bash
javac ECommerceAppBasics.java
```

### 4️⃣ Run the application

```bash
java ECommerceAppBasics
```

You may also run it using IntelliJ IDEA, Eclipse, NetBeans, or VS Code.

---

## 🧪 Core Concepts Demonstrated

* GUI programming using Swing
* Queue operations: `add()` and `poll()`
* Event-driven programming
* OOP concepts (encapsulation, classes, objects)
* Real-world e-commerce simulation

---

## 👩‍💻 Contributors

| Role                   | Contributor                                               |
| ---------------------- | --------------------------------------------------------- |
| **Backend Developer**  | [Joshin K Thomas](https://github.com/Joshin-07)          |
| **Frontend Developer** | [Karthik Raj] |
---

## 🏆 Highlights

* A clean introduction to **Queues in Java**
* Practical real-world simulation
* Excellent for academic submission or learning DS + OOP
* Beginner-friendly, easy to run and extend

---

<div align="center">

**"Learn Queue operations the fun way — through an E-commerce workflow."** 🛒✨

</div>

---

