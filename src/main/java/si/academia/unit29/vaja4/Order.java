package si.academia.unit29.vaja4;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Order {

    private int id;
    private String customer;
    private Date date;
    private float total;
    private String currency;

    public Order() {
    }

    public Order(int id, String customer, Date date, float total, String currency) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.total = total;
        this.currency = currency;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public float getTotal() { return total; }
    public void setTotal(float total) { this.total = total; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer='" + customer + '\'' +
                ", date=" + date +
                ", total=" + total +
                ", currency='" + currency + '\'' +
                '}';
    }

}
