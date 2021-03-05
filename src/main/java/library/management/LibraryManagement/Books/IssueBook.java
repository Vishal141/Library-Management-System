package library.management.LibraryManagement.Books;

import java.sql.Timestamp;

public class IssueBook {
    private String bookId;
    private String memberId;
    private Timestamp timestamp;
    private int renewCount;

    public IssueBook(String bookId, String memberId, Timestamp timestamp, int renewCount) {
        this.bookId = bookId;
        this.memberId = memberId;
        this.timestamp = timestamp;
        this.renewCount = renewCount;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getRenewCount() {
        return renewCount;
    }

    public void setRenewCount(int renewCount) {
        this.renewCount = renewCount;
    }
}
