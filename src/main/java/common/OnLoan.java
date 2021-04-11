package common;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OnLoan implements Serializable {

    private int loan_id;
    private int person_id;
    private int book_id;
    private int loan_period;
    private String loan_start;
    private String loan_end;
    private String returned_date;
    private String return_status;

    public OnLoan(int loan_id, int book_id, int person_id, int loan_period, String loan_start,String loan_end, String returned_date, String return_status){
        this.loan_id = loan_id;
        this.book_id = book_id;
        this.person_id = person_id;
        this.loan_period = loan_period;
        this.loan_start = loan_start;
        this.loan_end = loan_end;
        this.returned_date = returned_date;
        this.return_status = return_status;
    }

    public static OnLoan newLoanFromResultSet(ResultSet resultSet) throws SQLException {
        return new OnLoan(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getInt(3),
                resultSet.getInt(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8));
    }

    public int getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(int loan_id) {
        this.loan_id = loan_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getLoan_period() {
        return loan_period;
    }

    public void setLoan_period(int loan_period) {
        this.loan_period = loan_period;
    }

    public String getLoan_start() {
        return loan_start;
    }

    public void setLoan_start(String loan_start) {
        this.loan_start = loan_start;
    }

    public String getLoan_end() {
        return loan_end;
    }

    public void setLoan_end(String loan_end) {
        this.loan_end = loan_end;
    }

    public String getReturned_date() {
        return returned_date;
    }

    public void setReturned_date(String returned_date) {
        this.returned_date = returned_date;
    }

    public String getReturn_status() {
        return return_status;
    }

    public void setReturn_status(String return_status) {
        this.return_status = return_status;
    }

    public String toString(){
       return loan_id+
               "," + book_id+
               ","+person_id+
               ","+ loan_period+
               "," + loan_start+
               "," + loan_end+
               "," + returned_date+
               ","+ return_status;
    }

}
