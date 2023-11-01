import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://192.168.56.101:4567/madang",
                    "guseh5634", "gmlakd5634!");

            while (true) {
                System.out.println("1. 데이터 삽입");
                System.out.println("2. 데이터 삭제");
                System.out.println("3. 데이터 검색");
                System.out.println("4. 종료");
                System.out.print("메뉴를 선택하세요: ");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        insertData(con);
                        break;
                    case 2:
                        deleteData(con);
                        break;
                    case 3:
                        searchData(con);
                        break;
                    case 4:
                        con.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("잘못된 선택입니다.");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void insertData(Connection con) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("도서 번호를 입력하세요: ");
        int bookId = scanner.nextInt();
        System.out.print("도서 이름을 입력하세요: ");
        String bookName = scanner.next();
        System.out.print("출판사를 입력하세요: ");
        String publisher = scanner.next();
        System.out.print("가격을 입력하세요: ");
        int price = scanner.nextInt();

        String query = "INSERT INTO Book (bookId, bookName, publisher, price) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, bookId);
        preparedStatement.setString(2, bookName);
        preparedStatement.setString(3, publisher);
        preparedStatement.setInt(4, price);
        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("데이터가 삽입되었습니다.");
        }
    }

    public static void deleteData(Connection con) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 도서 번호를 입력하세요: ");
        int bookId = scanner.nextInt();

        String query = "DELETE FROM Book WHERE bookId = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, bookId);
        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("데이터가 삭제되었습니다.");
        } else {
            System.out.println("해당하는 데이터가 없습니다.");
        }
    }

    public static void searchData(Connection con) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("검색할 도서 번호를 입력하세요: ");
        int bookId = scanner.nextInt();

        String query = "SELECT * FROM Book WHERE bookId = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setInt(1, bookId);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt("bookId") + "  " + rs.getString("bookName") + "  " +
                    rs.getString("publisher") + "  " + rs.getInt("price"));
        }
    }
}
