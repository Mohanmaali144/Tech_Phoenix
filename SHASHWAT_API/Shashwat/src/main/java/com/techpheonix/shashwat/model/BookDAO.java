package com.techpheonix.shashwat.model;

import com.techpheonix.shashwat.service.GetConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO {

    public boolean addBook(BookDTO bookdto) throws ClassNotFoundException {
        Connection con = GetConnection.getConnection();
        System.out.println("Connection Done");
        boolean flag = false;
        int auth = checkAuthor(bookdto);
        int gen = checkGenre(bookdto);

        if (auth != 0 && gen != 0) {
            try {

                String sql = "insert into BookDetails(bookName, publishingYear,pageNo,img_path,pdf_path,genre_id,Author_id, description,freebook) values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, bookdto.getBookName());
                ps.setString(2, bookdto.getPublishingYear());
                ps.setInt(3, bookdto.getPageNo());
                ps.setString(4, bookdto.getImg());
                ps.setString(5, bookdto.getPdf());
                ps.setInt(6, gen);
                ps.setInt(7, auth);
                ps.setString(8, bookdto.getDiscription());
                ps.setBoolean(9, bookdto.isFreebook());
                if (ps.executeUpdate() > 0) {

                    System.out.println("Inserted");
                    flag = true;
                }

            } catch (SQLException e) {
                System.out.println(e);
                flag = false;
            }
        }

        return flag;
    }

    private int checkAuthor(BookDTO bookdto) throws ClassNotFoundException {
        Connection con = GetConnection.getConnection();

        try {

            String gen = "SELECT Author_id FROM AuthorInfo WHERE Author_name = ?";
            PreparedStatement ps = con.prepareStatement(gen);
            ps.setString(1, bookdto.getAuthorName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
//                return addAuthor(bookdao.getAuthorName());
                int author = addAuthor(bookdto);
                return author;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public int addAuthor(BookDTO bookdto) {
        Connection con = GetConnection.getConnection();

        try {
            System.out.println("nitin 2");

            String sql = "insert into authorinfo(Author_name) values(?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bookdto.getAuthorName());
            if (ps.executeUpdate() > 0) {

                System.out.println("Inserted");

                BookDAO bdao = new BookDAO();
                return bdao.checkAuthor(bookdto);

            } else {

            }

        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private int checkGenre(BookDTO bookdto) throws ClassNotFoundException {
        Connection con = GetConnection.getConnection();

        try {
            String gen = "SELECT genre_id FROM GenreInfo WHERE genre = ?";
            PreparedStatement ps = con.prepareStatement(gen);
            ps.setString(1, bookdto.getGenre());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                int genre = addGenre(bookdto);
                return genre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int addGenre(BookDTO bookdto) throws ClassNotFoundException {
        Connection con = GetConnection.getConnection();
        boolean flag = false;
        try {
            System.out.println("nitin 2");

            String sql = "insert into genreinfo (genre) values(?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, bookdto.getGenre());
            if (ps.executeUpdate() > 0) {

                System.out.println("Inserted");
                BookDAO bdao = new BookDAO();
                return bdao.checkGenre(bookdto);
            }

        } catch (SQLException e) {
            System.out.println(e);
            flag = false;
        }
        return 0;
    }

    public ArrayList<BookDTO> getAuthor() throws ClassNotFoundException {
        Connection con = GetConnection.getConnection();
        ResultSet rs = null;
        ArrayList<BookDTO> al = new ArrayList<BookDTO>();
        try {
            String sql = "select * from AuthorInfo;";
            PreparedStatement ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                BookDTO bookdto = new BookDTO();
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(2));
                bookdto.setAuthorId(rs.getInt(1));
                bookdto.setAuthorName(rs.getString(2));

                al.add(bookdto);

            }

//            System.out.println(al + "Added to array list");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return al;
    }

    public ArrayList<BookDTO> getGenre() throws ClassNotFoundException {
        Connection con = GetConnection.getConnection();
        ResultSet rs = null;
        ArrayList<BookDTO> al = new ArrayList<>();
        try {
            String sql = "select * from GenreInfo;";
            PreparedStatement ps = con.prepareStatement(sql);

            rs = ps.executeQuery();
            while (rs.next()) {
                BookDTO bookdto = new BookDTO();

                bookdto.setGenreId(rs.getInt(1));
                bookdto.setGenre(rs.getString(2));

                al.add(bookdto);

            }

            System.out.println(al + "Added to array list");
        } catch (SQLException e) {
            System.out.println(e);
        }
        return al;
    }

//    --------------get Book-------------------
    public ArrayList<BookDTO> getBook(ArrayList<BookDTO> bookdto) {
        boolean flag = false;
        Connection con = GetConnection.getConnection();
        String query = "SELECT bookdetails.book_id, bookdetails.bookName, bookdetails.publishingYear, "
                + "bookdetails.pageNo, bookdetails.img_path, bookdetails.pdf_path, bookdetails.description,bookdetails.freebook, bookdetails.genre_id AS book_genre_id,"
                + " bookdetails.Author_id AS book_author_id, authorinfo.Author_id, authorinfo.Author_name, "
                + "genreinfo.genre_id AS genre_genre_id, genreinfo.genre FROM bookdetails JOIN authorinfo "
                + "ON bookdetails.Author_id = authorinfo.Author_id JOIN genreinfo ON bookdetails.genre_id = genreinfo.genre_id;";

        try {

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookDTO bdao = new BookDTO();

                bdao.setBookId(rs.getInt("book_id"));
                bdao.setBookName(rs.getString("bookName"));
                bdao.setPublishingYear(rs.getString("publishingYear"));
                bdao.setPageNo(rs.getInt("pageNo"));
                bdao.setImg(rs.getString("img_path"));
                bdao.setPdf(rs.getString("pdf_path"));
                bdao.setPageNo(rs.getInt("pageNo"));
                bdao.setAuthorId(rs.getInt("Author_id"));
                bdao.setDiscription(rs.getString("description"));
                bdao.setFreebook(rs.getBoolean("freebook"));
                System.out.println("comming in getbook dto");
                bdao.setGenreId(rs.getInt("book_genre_id"));

                bdao.setAuthorName(rs.getString("Author_name"));
                bdao.setGenre(rs.getString("genre"));
                bookdto.add(bdao);
                flag = true;
            }
        } catch (SQLException e) {

            System.out.println(e);
            flag = false;
        }

        return bookdto;
    }

    public boolean getGenreName(BookDTO bdto) throws ClassNotFoundException {
        Connection con = GetConnection.getConnection();
        ResultSet rs = null;
        boolean flag = false;
        try {
            String sql = "select * from GenreInfo WHERE genre_id  = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bdto.getGenreId());
            rs = ps.executeQuery();
            while (rs.next()) {
                bdto.setGenre(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return flag;
    }

    public boolean getAuthorName(BookDTO bdto) throws ClassNotFoundException {
        Connection con = GetConnection.getConnection();
        ResultSet rs = null;
        boolean flag = false;
        try {
            String sql = "select * from authorInfo WHERE Author_id  = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bdto.getAuthorId());
            rs = ps.executeQuery();
            while (rs.next()) {
                bdto.setAuthorName(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return flag;
    }

//    ---------------------------get by genre name---------------------
    public boolean getCategoryBook(ArrayList<BookDTO> bcategory, String category) {
        Connection con = GetConnection.getConnection();
        ResultSet rs = null;
        boolean flag = false;
        try {
            String sql = "SELECT \n"
                    + "    bookdetails.book_id, \n"
                    + "    bookdetails.bookName, \n"
                    + "    bookdetails.publishingYear, \n"
                    + "    bookdetails.pageNo, \n"
                    + "    bookdetails.img_path, \n"
                    + "    bookdetails.pdf_path, \n"
                    + "    bookdetails.genre_id AS book_genre_id,\n"
                    + "    bookdetails.Author_id AS book_author_id, \n"
                    + "    authorinfo.Author_id, \n"
                    + "    authorinfo.Author_name, \n"
                    + "    genreinfo.genre_id AS genre_genre_id, \n"
                    + "    genreinfo.genre \n"
                    + "FROM \n"
                    + "    bookdetails\n"
                    + "JOIN \n"
                    + "    authorinfo ON bookdetails.Author_id = authorinfo.Author_id\n"
                    + "JOIN \n"
                    + "    genreinfo ON bookdetails.genre_id = genreinfo.genre_id\n"
                    + "WHERE \n"
                    + "    genre = ?;"
                    + "";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, category);
            rs = ps.executeQuery();
            while (rs.next()) {
                BookDTO bdao = new BookDTO();
                bdao.setBookId(rs.getInt("book_id"));
                bdao.setBookName(rs.getString("bookName"));
                bdao.setPublishingYear(rs.getString("publishingYear"));
                bdao.setPageNo(rs.getInt("pageNo"));
                bdao.setImg(rs.getString("img_path"));
                bdao.setPdf(rs.getString("pdf_path"));
                bdao.setPageNo(rs.getInt("pageNo"));
                bdao.setAuthorId(rs.getInt("Author_id"));

                System.out.println("comming in getbook dto");
                bdao.setGenreId(rs.getInt("book_genre_id"));

                bdao.setAuthorName(rs.getString("Author_name"));
                bdao.setGenre(rs.getString("genre"));
                bcategory.add(bdao);

                System.out.println("coming in database");
                flag = true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return flag;
    }

//    =============== bookdetails ===================
    public boolean getBookDetail(ArrayList<BookDTO> bookdto, int bookId) {
        boolean flag = false;
        Connection con = GetConnection.getConnection();
        System.out.println("BookId : " + bookId);
        String query = "SELECT bookdetails.book_id, bookdetails.bookName, bookdetails.publishingYear, "
                + "bookdetails.pageNo, bookdetails.img_path, bookdetails.pdf_path, bookdetails.description,bookdetails.freebook, bookdetails.genre_id AS book_genre_id,"
                + " bookdetails.Author_id AS book_author_id, authorinfo.Author_id, authorinfo.Author_name, "
                + "genreinfo.genre_id AS genre_genre_id, genreinfo.genre FROM bookdetails JOIN authorinfo "
                + "ON bookdetails.Author_id = authorinfo.Author_id JOIN genreinfo ON bookdetails.genre_id = genreinfo.genre_id where book_id=?;";

        try {

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookDTO bdao = new BookDTO();

                bdao.setBookId(rs.getInt("book_id"));
                bdao.setBookName(rs.getString("bookName"));
                bdao.setPublishingYear(rs.getString("publishingYear"));
                bdao.setPageNo(rs.getInt("pageNo"));
                bdao.setImg(rs.getString("img_path"));
                bdao.setPdf(rs.getString("pdf_path"));
                bdao.setPageNo(rs.getInt("pageNo"));
                bdao.setAuthorId(rs.getInt("Author_id"));
                bdao.setDiscription(rs.getString("description"));
                bdao.setFreebook(rs.getBoolean("freebook"));
                System.out.println("comming in getbook dto");
                bdao.setGenreId(rs.getInt("book_genre_id"));

                bdao.setAuthorName(rs.getString("Author_name"));
                bdao.setGenre(rs.getString("genre"));
                bookdto.add(bdao);
                flag = true;
            }
        } catch (SQLException e) {

            System.out.println(e);
            flag = false;
        }

        return flag;
    }

//  =========Delete Book===========
    public boolean deleteBook(int bid) {
        Connection con = GetConnection.getConnection();
        boolean flag = false;
        try {
            String sql = "DELETE FROM bookdetails WHERE book_id  = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bid);

            if (ps.executeUpdate() > 0) {

                flag = true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return flag;
    }

//   ===============book total ================= 
    public int getTotalBook() {
        Connection con = GetConnection.getConnection();
        String query = "SELECT COUNT(book_id) FROM bookdetails";
        int result = 0;
        try {

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("some Exception");
            System.out.println(e);
        }

        return result;
    }

}
