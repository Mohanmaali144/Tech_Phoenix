
package com.techpheonix.shashwat.model;

import com.techpheonix.shashwat.model.UserDAO;

public class BookDTO extends UserDAO {

    private int bookId, pageNo, genreId, authorId;
    private String bookName, publishingYear, img, pdf, authorName, genre, discription;
    boolean freebook;

    public BookDTO() {
    }

    public BookDTO(int pageNo, int genreId, int authorId, String bookName, String publishingYear, String img, String pdf, String authorName, String genre, String discription, boolean freebook) {

        this.pageNo = pageNo;
        this.genreId = genreId;
        this.authorId = authorId;
        this.bookName = bookName;
        this.publishingYear = publishingYear;
        this.img = img;
        this.pdf = pdf;
        this.authorName = authorName;
        this.genre = genre;
        this.discription = discription;
        this.freebook = freebook;
    }

    public boolean isFreebook() {
        return freebook;
    }

    public void setFreebook(boolean freebook) {
        this.freebook = freebook;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear = publishingYear;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
