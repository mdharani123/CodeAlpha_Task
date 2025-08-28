import java.util.*;

// Stock class
class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

// Portfolio class
class Portfolio {
    private Map<String, Integer> holdings;
    private double balance;

    public Portfolio(double initialBalance) {
        this.balance = initialBalance;
        this.holdings = new HashMap<>();
    }

    public void buyStock(Stock stock, int quantity) {
        double cost = stock.getPrice() * quantity;
        if (balance >= cost) {
            balance -= cost;
            holdings.put(stock.getSymbol(), holdings.getOrDefault(stock.getSymbol(), 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    public void sellStock(Stock stock, int quantity) {
        if (holdings.getOrDefault(stock.getSymbol(), 0) >= quantity) {
            balance += stock.getPrice() * quantity;
            holdings.put(stock.getSymbol(), holdings.get(stock.getSymbol()) - quantity);
            System.out.println("Sold " + quantity + " shares of " + stock.getSymbol());
        } else {
            System.out.println("You don't own that many shares.");
        }
    }

    public void viewPortfolio(Map<String, Stock> market) {
        System.out.println("\n--- Portfolio ---");
        System.out.println("Balance: ₹" + balance);
        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            String symbol = entry.getKey();
            int qty = entry.getValue();
            double currentValue = qty * market.get(symbol).getPrice();
            System.out.println(symbol + " - Quantity: " + qty + ", Value: ₹" + currentValue);
        }
    }
}

// TradingPlatform class
public class StockTradingPlatform {
    private Map<String, Stock> market;
    private Portfolio portfolio;
    private Scanner scanner;

    public StockTradingPlatform() {
        this.market = new HashMap<>();
        this.scanner = new Scanner(System.in);
        this.portfolio = new Portfolio(10000.0); // starting balance

        // Add some demo stocks
        market.put("TCS", new Stock("TCS", 3500.0));
        market.put("INFY", new Stock("INFY", 1500.0));
        market.put("HDFC", new Stock("HDFC", 2700.0));
        market.put("RELIANCE", new Stock("RELIANCE", 2500.0));
    }

    public void displayMarket() {
        System.out.println("\n--- Market Data ---");
        for (Stock stock : market.values()) {
            System.out.println(stock.getSymbol() + " - Price: ₹" + stock.getPrice());
        }
    }

    public void run() {
        while (true) {
            System.out.println("\n1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> displayMarket();
                case 2 -> buyStock();
                case 3 -> sellStock();
                case 4 -> portfolio.viewPortfolio(market);
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private void buyStock() {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (market.containsKey(symbol)) {
            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();
            portfolio.buyStock(market.get(symbol), qty);
        } else {
            System.out.println("Stock not found!");
        }
    }

    private void sellStock() {
        System.out.print("Enter stock symbol: ");
        String symbol = scanner.next().toUpperCase();
        if (market.containsKey(symbol)) {
            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();
            portfolio.sellStock(market.get(symbol), qty);
        } else {
            System.out.println("Stock not found!");
        }
    }

    public static void main(String[] args) {
        StockTradingPlatform platform = new StockTradingPlatform();
        platform.run();
    }
}
