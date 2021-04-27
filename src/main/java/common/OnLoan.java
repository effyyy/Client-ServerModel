package common;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates an object for the on_loan database
 */
public class OnLoan implements Serializable {

    private final int loan_id;
    private final int person_id;
    private final int book_id;
    private final int loan_period;
    private final String loan_start;
    private final String loan_end;
    private final String returned_date;
    private final String return_status;

    /**
     * This is the constructor for the on_loan class, parameters are the columns in the database and each input parameter
     * defines the value for that particular column in the database.
     * @param loan_id
     * @param book_id
     * @param person_id
     * @param loan_period
     * @param loan_start
     * @param loan_end
     * @param returned_date
     * @param return_status
     */
    public OnLoan(int loan_id, int book_id, int person_id, int loan_period, String loan_start, String loan_end, String returned_date, String return_status) {
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

    public String toString() {
        return loan_id +
                "," + book_id +
                "," + person_id +
                "," + loan_period +
                "," + loan_start +
                "," + loan_end +
                "," + returned_date +
                "," + return_status;
    }

}
