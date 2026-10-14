 package WEEK8.assigment_problems;

import java.util.ArrayList;
import java.util.List;

// Interface for pluggable pricing plans
interface CardPricingPlan {
    double getDiscountRate();

    String getPlanName();
}

class DayScholarPlan implements CardPricingPlan {
    public double getDiscountRate() {
        return 0.0;
    }

    public String getPlanName() {
        return "Day Scholar";
    }
}

class HostellerPlan implements CardPricingPlan {
    public double getDiscountRate() {
        return 0.10;
    }

    public String getPlanName() {
        return "Hosteller";
    }
}

class StaffPlan implements CardPricingPlan {
    public double getDiscountRate() {
        return 0.20;
    }

    public String getPlanName() {
        return "Staff";
    }
}

enum CardTransactionType {
    TOP_UP,
    PURCHASE,
    REFUND
}

// Represents one balance-changing transaction
class CardTransaction {
    private final CardTransactionType type;
    private final String description;
    private final double amount;
    private final double balanceAfter;

    public CardTransaction(CardTransactionType type, String description,
                           double amount, double balanceAfter) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public CardTransactionType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }
}

// Tracks a purchase and whether it has already been refunded
class CardFoodPurchase {
    private final String itemName;
    private final double chargedAmount;
    private boolean refunded;

    public CardFoodPurchase(String itemName, double chargedAmount) {
        this.itemName = itemName;
        this.chargedAmount = chargedAmount;
        this.refunded = false;
    }

    public String getItemName() {
        return itemName;
    }

    public double getChargedAmount() {
        return chargedAmount;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void markRefunded() {
        refunded = true;
    }
}

// Smart card manages balance, transactions, purchases and blocking
class SmartCard {
    private static final double MIN_TOP_UP = 100.0;
    private static final double MAX_BALANCE = 5000.0;

    private final String cardId;
    private final CardPricingPlan pricingPlan;
    private final List<CardTransaction> transactions;
    private final List<CardFoodPurchase> purchases;

    private double balance;
    private boolean blocked;

    public SmartCard(String cardId, CardPricingPlan pricingPlan) {
        this.cardId = cardId;
        this.pricingPlan = pricingPlan;
        this.balance = 0.0;
        this.blocked = false;
        this.transactions = new ArrayList<>();
        this.purchases = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public String getCardId() {
        return cardId;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void blockCard() {
        blocked = true;
        System.out.println("Card " + cardId + " is blocked.");
    }

    public void unblockCard() {
        blocked = false;
        System.out.println("Card " + cardId + " is unblocked.");
    }

    public void topUp(double amount) {
        if (blocked) {
            System.out.println("Top-up failed: card is blocked.");
            return;
        }

        if (amount < MIN_TOP_UP) {
            System.out.println("Top-up failed: minimum top-up is ₹"
                    + format(MIN_TOP_UP) + ".");
            return;
        }

        if (balance + amount > MAX_BALANCE) {
            System.out.println("Top-up failed: maximum balance is ₹"
                    + format(MAX_BALANCE) + ".");
            return;
        }

        balance = roundMoney(balance + amount);

        transactions.add(new CardTransaction(
                CardTransactionType.TOP_UP,
                "Top-up",
                amount,
                balance
        ));

        System.out.println("Top-up successful: ₹" + format(amount)
                + " added. Balance: ₹" + format(balance) + ".");
    }

    public void purchase(String itemName, double originalPrice) {
        if (blocked) {
            System.out.println("Purchase failed: card is blocked.");
            return;
        }

        if (originalPrice <= 0) {
            System.out.println("Purchase failed: invalid item price.");
            return;
        }

        double discount = pricingPlan.getDiscountRate();
        double chargedAmount = roundMoney(
                originalPrice * (1.0 - discount)
        );

        if (balance < chargedAmount) {
            System.out.println("Purchase failed: insufficient balance. "
                    + "Required: ₹" + format(chargedAmount)
                    + ", available: ₹" + format(balance) + ".");
            return;
        }

        balance = roundMoney(balance - chargedAmount);

        CardFoodPurchase purchase =
                new CardFoodPurchase(itemName, chargedAmount);

        purchases.add(purchase);

        transactions.add(new CardTransaction(
                CardTransactionType.PURCHASE,
                itemName,
                -chargedAmount,
                balance
        ));

        System.out.println(itemName + " purchased for ₹"
                + format(chargedAmount) + " ("
                + (int) (discount * 100) + "% discount). Balance: ₹"
                + format(balance) + ".");
    }

    public void refund(CardFoodPurchase purchase) {
        if (blocked) {
            System.out.println("Refund failed: card is blocked.");
            return;
        }

        if (!purchases.contains(purchase)) {
            System.out.println("Refund failed: purchase does not belong "
                    + "to this card.");
            return;
        }

        if (purchase.isRefunded()) {
            System.out.println("Refund failed: "
                    + purchase.getItemName()
                    + " has already been refunded.");
            return;
        }

        double refundAmount = purchase.getChargedAmount();

        if (balance + refundAmount > MAX_BALANCE) {
            System.out.println("Refund failed: maximum balance would "
                    + "be exceeded.");
            return;
        }

        balance = roundMoney(balance + refundAmount);
        purchase.markRefunded();

        transactions.add(new CardTransaction(
                CardTransactionType.REFUND,
                purchase.getItemName() + " refund",
                refundAmount,
                balance
        ));

        System.out.println("Refund successful for "
                + purchase.getItemName() + ": ₹"
                + format(refundAmount) + ". Balance: ₹"
                + format(balance) + ".");
    }

    public CardFoodPurchase findPurchase(String itemName) {
        for (CardFoodPurchase purchase : purchases) {
            if (purchase.getItemName().equals(itemName)) {
                return purchase;
            }
        }

        return null;
    }

    public void printStatement() {
        System.out.println("\nTransaction Statement for " + cardId);
        System.out.println("Pricing plan: " + pricingPlan.getPlanName());
        System.out.println("----------------------------------------");

        for (CardTransaction transaction : transactions) {
            String sign = transaction.getAmount() >= 0 ? "+" : "-";
            double amount = Math.abs(transaction.getAmount());

            System.out.println(transaction.getType() + " | "
                    + transaction.getDescription() + " | "
                    + sign + "₹" + format(amount)
                    + " | Balance: ₹"
                    + format(transaction.getBalanceAfter()));
        }

        System.out.println("----------------------------------------");
        System.out.println("Final balance: ₹" + format(balance));
    }

    private static double roundMoney(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    private static String format(double amount) {
        return String.format("%.2f", amount);
    }
}

// Main class
public class Question5 {
    public static void main(String[] args) {
        SmartCard card = new SmartCard(
                "C-2045",
                new HostellerPlan()
        );

        card.topUp(500);

        card.purchase("Veg Thali", 120);
        card.purchase("Cold Coffee", 60);
        card.purchase("Special Meal", 400);

        CardFoodPurchase thali = card.findPurchase("Veg Thali");

        if (thali != null) {
            card.refund(thali);
            card.refund(thali);
        }

        card.printStatement();
    }
}
