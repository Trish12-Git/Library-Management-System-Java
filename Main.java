import java.util.*;
import java.io.Serializable;

class Book implements Serializable{
    static int bookcount=1;
    int bookID;
    String bookName;
    String writerName;
    double price;
    int quantity;

    public Book(int bookID,String bookName,String writerName,double price,int quantity){
        this.bookID=bookcount++;
        this.bookName=bookName;
        this.writerName=writerName;
        this.price=price;
        this.quantity=quantity;
    }
    public void display(){
        System.out.println(bookID +" | "+bookName+" | "+writerName+" | "+price+" | "+quantity);
    }
}
class User implements Serializable{
    static int userCount=1;

    int userID;
    String password;
    String role;

    public User(String password,String role){
        this.userID=userCount++;
        this.password=password;
        this.role=role;
    }
}
class issueBook implements Serializable{
    int bookID;
    int userID;

    public issueBook(int bookID,int userID){
        this.bookID=bookID;
        this.userID=userID;
    }
}
public class Main {

    private static final String BOOK_FILE = "bookDetails.txt";
    private static final String USER_FILE = "userDetails.txt";
    private static final String ISSUE_FILE = "issueBook.txt";

    public static void searchByBookName(ArrayList<Book> books,String name){
           boolean found = false;
           for(Book B : books){
            if(B.bookName.toLowerCase().contains(name.toLowerCase())){
                B.display();
                found = true;
            }
        }
        if(!found){
            System.out.println("Book Absent.");
        }
    }
    public static void searchByWriterName(ArrayList<Book> books,String writer){
        boolean found = false;
        for(Book B : books){
            if(B.writerName.toLowerCase().contains(writer.toLowerCase())){
                B.display();
                found=true;
            }
        }
        if(!found){
            System.out.println("Writer Absent.");
        }
    }
    public static void main(String args[]){
    Scanner sc = new Scanner(System.in);
    //DS to store Book details:-
    ArrayList<Book> books = (ArrayList<Book>) FileUtil.loadObject(BOOK_FILE);
    if (books == null) books = new ArrayList<>();
    //DS to store user details:-
    ArrayList<User> users = (ArrayList<User>) FileUtil.loadObject(USER_FILE);
    if (users == null) users = new ArrayList<>();
    //DS to store admins who issued books:-
    ArrayList<issueBook> issuedBooks =(ArrayList<issueBook>) FileUtil.loadObject(ISSUE_FILE);
    
    if (issuedBooks == null) issuedBooks = new ArrayList<>();

    if (!books.isEmpty()) {
    Book.bookcount = books.get(books.size() - 1).bookID + 1;
    }

    if (!users.isEmpty()) {
    User.userCount = users.get(users.size() - 1).userID + 1;
    }

    int choice;
       do{
        System.out.println("--Library Menu--");
        System.out.println("Choice 1: Add Book");
        System.out.println("Choice 2: View Book");
        System.out.println("Choice 3: Delete Book");
        System.out.println("Choice 4: Reduce Quantity");
        System.out.println("Choice 5: To issue books");
        System.out.println("Choice 6: Exit");
        System.out.println("Please enter your choice!");
        choice=sc.nextInt();

        switch(choice) {
            case 1:
                sc.nextLine();
                System.out.println("Enter bookName");
                String bookName=sc.nextLine();
                System.out.println("Enter writerName");
                String WriterName=sc.nextLine();
                System.out.println("Enter book price");
                double price=sc.nextDouble();
                System.out.println("Enter quantity of book");
                int quantity=sc.nextInt();

                books.add(new Book(0, bookName, WriterName, price, quantity));
                FileUtil.saveObject(BOOK_FILE, books);
                System.out.println("Book added successfully!!😃");
                break;
            
            case 2:
                 if(books.isEmpty()){
                    System.out.println("Sorry , no books are available! ");
                 }else{
                System.out.println("Choice A : View entire Library");
                System.out.println("Choice B : Search Book by BookName");
                System.out.println("Choice C : Search book by Author's name");
                System.out.println("Please enter your choice.");
                char C = sc.next().charAt(0);
                sc.nextLine();
                switch(C){
                    case 'A':
                        for(Book b:books){
                        b.display();
                        }
                        break;
                    case 'B':
                        System.out.println("Enter BookName");
                        String name = sc.nextLine();
                        searchByBookName(books, name);
                        break;
                    case 'C':
                        System.out.println("Enter Author's name");
                        String name2 = sc.nextLine();
                        searchByWriterName(books, name2);
                        break;
               }
                
            }
            break;
            
            case 3:
                //Iterator because it avoids concurrent modification exception.
                System.out.println("Enter the book ID you want to delete.");
                int deleteID = sc.nextInt();
                boolean found = false;
                Iterator<Book> It = books.iterator();
                while(It.hasNext()){
                    Book b = It.next();
                    if(b.bookID==deleteID){
                        It.remove();
                        FileUtil.saveObject(BOOK_FILE, books);
                        found=true;
                        System.out.println("Book deleted successfully!");
                        break;
                    }
                }
                if(!found){
                    System.out.println("Oops! Book not found!");
                }
                break;

                case 4:
                System.out.println("Enter book ID.");
                int ID = sc.nextInt();
                System.out.println("Enter the quantity you want to reduce.");
                int reduceQty = sc.nextInt();

                boolean exists=false;
                for(Book b : books){
                    if(b.bookID == ID){
                       exists=true;
                    }
                    if(reduceQty>b.quantity){
                        System.out.println("Not enough books to reduce");
                    }else{
                        b.quantity-=reduceQty;
                        FileUtil.saveObject(BOOK_FILE, books);
                    }
                }
                if(!exists){
                    System.out.println("Book not found");
                }
                break;
                //Issue Books:-
                case 5:
                    String loginPass="Admin@159Lib.in";
                    System.out.println("Enter your role!");
                    String role = sc.nextLine();
                    if(!role.equalsIgnoreCase("admin")){
                        System.out.println("Sorry, only admins can issue books.");
                        break;
                    }
                    System.out.println("Enter password");
                    String pass = sc.nextLine();
                    if(!pass.equals(loginPass)){
                        System.out.println("❌ Wrong password, access denied.");
                        break;
                    }else{
                        System.out.println("Access Granted!✅");
                    }
                    System.out.println("Enter user ID.");
                    int uid=sc.nextInt();
                    System.out.println("Enter book ID.");
                    int bid=sc.nextInt();

                    Book B = null;
                    for(Book BK:books){
                        if(BK.bookID==bid){
                           B=BK;
                           break;
                        }
                    }
                    if(B==null){
                        System.out.println("Book not found.");
                        break;
                    }
                    if(B.quantity<=0){
                        System.out.println("Book Unavailable.");
                    }
                    boolean alreadyIssued = false;
                    for(issueBook ib:issuedBooks){
                        if(uid==ib.userID&&B.bookID==bid){
                          alreadyIssued=true;
                          break;
                        }
                    }
                    if(alreadyIssued){
                        System.out.println("❌ Already Issued.");
                        break;
                    }
                    issuedBooks.add(new issueBook(uid, bid));
                    B.quantity--;
                    FileUtil.saveObject(ISSUE_FILE, issuedBooks);
                    FileUtil.saveObject(BOOK_FILE, books);
                    System.out.println("Book successfully Issued!");
                    break;

                case 6:
                System.out.println("Exiting...");
                break;
                default:System.out.println("Invalid Choice!");
            }
        }while(choice!=3);
        sc.close();
    }
}
