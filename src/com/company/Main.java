package com.company;

import org.w3c.dom.*;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import org.xml.sax.SAXException;;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;




public class Main {
    static Document xmlDocument = null;
    static String dateFormat="dd/MM/yyyy";
    static void Menu(){
        System.out.println("1-insert Books");
        System.out.println("2-Search Books");
        System.out.println("3-Delete Books");
        System.out.println("4-Update Books");
        System.out.println("5-Sort Books");
    }
    static void SearchAttributesMenu(){
        System.out.println("1-Author");
        System.out.println("2-Title");
        System.out.println("3-ID");
        System.out.println("4-Genre");
        System.out.println("5-Price");
        System.out.println("6-Publish_Date");
        System.out.println("7-Description");
    }
    static void GenreMenu(){
        System.out.println("1-Science");
        System.out.println("2-Fiction");
        System.out.println("3-Drama");
    }
    static void AttributesMenu(){
        System.out.println("1-Author");
        System.out.println("2-Title");
        System.out.println("3-Genre");
        System.out.println("4-Price");
        System.out.println("5-Publish_Date");
        System.out.println("6-Description");
    }
    static void SortAttributesMenu(){
        System.out.println("1-Author");
        System.out.println("2-Title");
        System.out.println("3-Genre");
        System.out.println("4-ID");
        System.out.println("5-Publish_Date");
    }
    static void TypeOfSortedMenu(){
        System.out.println("1-ASE");
        System.out.println("2-DES");
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    private static List<Book> SearchBooksByTitle(String Title,List<Book> AllBooks){
        List<Book> Result = new ArrayList<Book>();
        for (Book book:AllBooks) {
            if(book.Title.equalsIgnoreCase(Title)){
                Result.add(book);
            }
        }
        return Result;
    }
    private static List<Book> SearchBooksByAuthor(String Author,List<Book> AllBooks){
        List<Book> Result = new ArrayList<Book>();
        for (Book book:AllBooks) {
            if(book.Author.equalsIgnoreCase(Author)){
                Result.add(book);
            }
        }
        return Result;
    }
    private static List<Book> SearchBooksByDescription(String Description, List<Book> AllBooks){
        List<Book> Result = new ArrayList<Book>();
        for (Book book:AllBooks) {
            if(book.Description.contains(Description)){
                Result.add(book);
            }
        }
        return Result;
    }
    private static List<Book> SearchBooksByGenre(String Genre,List<Book> AllBooks){
        List<Book> Result = new ArrayList<Book>();
        for (Book book:AllBooks) {
            if(book.Genre.equalsIgnoreCase(Genre)){
                Result.add(book);
            }
        }
        return Result;
    }
    private static List<Book> SearchBooksByPrice(double Price,List<Book> AllBooks){
        List<Book> Result = new ArrayList<Book>();
        for (Book book:AllBooks) {
            if(book.Price==Price){
                Result.add(book);
            }
        }
        return Result;
    }
    private static List<Book> SearchBooksByPublishDate(String Publish_Date, List<Book> AllBooks){
        List<Book> Result = new ArrayList<Book>();
        for (Book book:AllBooks) {
            if(book.Publish_Date.equals(Publish_Date)){
                Result.add(book);
            }
        }
        return Result;
    }
    private static Book SearchBooksByID(String ID,List<Book> AllBooks){
        for (Book book:AllBooks) {
            if(book.ID.equals(ID)){
                return book;
            }
        }
        return null;
    }
    private static void UpdatedBook(String BookID,Book book) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File file=new File( "Books.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File( "Books.xml"));
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                if(node.getAttributes().getNamedItem("ID").getNodeValue().equals(BookID)){
                    if(book.Genre!=null)elem.getElementsByTagName("Genre").item(0).getChildNodes().item(0).setNodeValue(book.Genre);
                    if(book.Price!=0.0)elem.getElementsByTagName("Price").item(0).getChildNodes().item(0).setNodeValue(String.valueOf(book.Price));
                    if(book.Author!=null)elem.getElementsByTagName("Author").item(0).getChildNodes().item(0).setNodeValue(book.Author);
                    if(book.Publish_Date!=null)elem.getElementsByTagName("Publish_Date").item(0).getChildNodes().item(0).setNodeValue(String.valueOf(book.Publish_Date));
                    if(book.Description!=null)elem.getElementsByTagName("Description").item(0).getChildNodes().item(0).setNodeValue(book.Description);
                    if(book.Title!=null)elem.getElementsByTagName("Title").item(0).getChildNodes().item(0).setNodeValue(book.Title);
                    System.out.println("Updated done");
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }
        List<Book> ReadedBooks = ReadBooks();
        Book book1=SearchBooksByID(BookID,ReadedBooks);
        System.out.println(book1);
    }
    private static void DeletedBook(String BookID) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File file=new File( "Books.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File( "Books.xml"));
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                if(node.getAttributes().getNamedItem("ID").getNodeValue().equals(BookID)){
                    System.out.println("done");
                    elem.getParentNode().removeChild(elem);
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        }
    }
    private static void DeletedAllBook(List<Book> ReadedBooks) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        for (Book book:ReadedBooks) {
            DeletedBook(book.ID);
        }
    }
    private static List<Book> ReadBooks(){
        List<Book> books = new ArrayList<Book>();
        try {
            //Get Document Builder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load the input XML document, parse it and return an instance of the
            // Document class.
            Document document = builder.parse(new File("Books.xml"));

            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    // Get the value of the ID attribute.
                    String ID = node.getAttributes().getNamedItem("ID").getNodeValue();

                    // Get the value of all sub-elements.
                    String Author = elem.getElementsByTagName("Author")
                            .item(0).getChildNodes().item(0).getNodeValue();

                    String Title = elem.getElementsByTagName("Title").item(0)
                            .getChildNodes().item(0).getNodeValue();
                    String Genre = elem.getElementsByTagName("Genre")
                            .item(0).getChildNodes().item(0).getNodeValue();

                    double Price = Double.parseDouble(elem.getElementsByTagName("Price").item(0)
                            .getChildNodes().item(0).getNodeValue());

                    String Publish_Date = elem.getElementsByTagName("Publish_Date")
                            .item(0).getChildNodes().item(0).getNodeValue();
                    String Description = elem.getElementsByTagName("Description").item(0)
                            .getChildNodes().item(0).getNodeValue();



                    books.add(new Book(ID, Author, Title, Genre,Price,Publish_Date,Description));
                }
            }


        } catch (Exception ignored) {
        }
        return books;
    }
    private static void writeBook(List<Book> booksWrite) throws ParserConfigurationException, IOException, TransformerException, SAXException {
        File file=new File( "Books.xml");
        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();

        Element root=null;
        if(file.length()>0){
            xmlDocument=documentBuilder.parse(file);
            root=xmlDocument.getDocumentElement();

        }
        else{
            xmlDocument=documentBuilder.newDocument();
            root= xmlDocument.createElement("Catalogue");
            xmlDocument.appendChild(root);
        }
        //root Element
        for (Book book : booksWrite) {
            // record element
            Element record = xmlDocument.createElement("Book");
            record.setAttribute("ID",book.ID);
            //Elements for each record
            Element Author = xmlDocument.createElement("Author");
            Element Title = xmlDocument.createElement("Title");
            Element Genre = xmlDocument.createElement("Genre");
            Element Price = xmlDocument.createElement("Price");
            Element Publish_Date = xmlDocument.createElement("Publish_Date");
            Element Description = xmlDocument.createElement("Description");
            //Data
            Text Author_text = xmlDocument.createTextNode(book.Author);
            Text Title_text = xmlDocument.createTextNode(book.Title);
            Text Genre_text = xmlDocument.createTextNode(book.Genre);
            Text Price_text = xmlDocument.createTextNode(String.valueOf(book.Price));
            Text Publish_Date_text = xmlDocument.createTextNode(String.valueOf(book.Publish_Date));
            Text Description_text = xmlDocument.createTextNode(book.Description);
            //add data to element
            Author.appendChild(Author_text);
            Title.appendChild(Title_text);
            Genre.appendChild(Genre_text);
            Price.appendChild(Price_text);
            Publish_Date.appendChild(Publish_Date_text);
            Description.appendChild(Description_text);
            //add element to record
            record.appendChild(Author);
            record.appendChild(Title);
            record.appendChild(Genre);
            record.appendChild(Price);
            record.appendChild(Publish_Date);
            record.appendChild(Description);
            //add record to root
            root.appendChild(record);
        }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(xmlDocument);
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);

    }
    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException, ParseException {
        List<Book> resultSearch= ReadBooks();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            List<Book> InsertedBooks =new ArrayList<Book>();
           /* if (resultSearch.size()<=1){
                if (resultSearch.size() > 0) {
                    System.out.println("Number of Books: "+resultSearch.size());
                    for (Book book : resultSearch) {
                        System.out.println(book.toString());
                    }
                }
                else {
                    System.out.println("No Books");
                }
                break;
            }*/
            Menu();
            int option = scanner.nextInt();
            if (option == 1) {
                System.out.println("Enter Number of Books");
                int numberOFBooks = scanner.nextInt();
                for (int i = 0; i < numberOFBooks; i++) {
                    System.out.println("Book " + (i + 1));
                    System.out.println("ID");
                    String ID="";
                    while (true) {
                        ID = scanner.next();
                        List<Book> ReadedBooks = ReadBooks();
                        Book result = SearchBooksByID(ID, ReadedBooks);
                        if(result==null)break;
                        System.out.println("duplicate ID please reenter another id");
                    }
                    System.out.println("Author");
                    String Author = scanner.next();
                    while(!Author.matches("[a-zA-Z]+")){
                        System.out.println("Must be char only");
                        System.out.println("Author");
                        Author = scanner.next();
                    }
                    System.out.println("Title");
                    String Title = scanner.next();
                    GenreMenu();
                    String Genre = "";
                    while (true) {
                        System.out.println("Enter Genre Number:");
                        int n = scanner.nextInt();
                        if (n == 1) {Genre = "Science";break;}
                        if (n == 2) {Genre = "Fiction";break;}
                        if (n == 3) {Genre = "Drama";break;}
                        else System.out.println("please enter 1,2,3");
                    }
                    System.out.println("Price");
                    double Price = 0.0;
                    while (true) {
                        String Price_input = scanner.next();
                        if (isNumeric(Price_input)) {
                            Price = Double.parseDouble(Price_input);
                            break;
                        }
                        else {
                            System.out.println("please enter number");
                        }
                    }
                    String Publish_Date=null;
                    while (true) {
                        System.out.println("Enter Date in format MM/DD/yyyy");
                        String stringDate = scanner.next();
                        if (isValid(stringDate)) {
                            Publish_Date = stringDate;
                            break;
                        }
                    }

                    System.out.println("Description");
                    String Description = scanner.next();
                    Book book = new Book(ID, Author, Title, Genre, Price, Publish_Date, Description);
                    InsertedBooks.add(book);
                }
                writeBook(InsertedBooks);
            }
            else if(option == 2){
                while (true) {
                    SearchAttributesMenu();
                    System.out.println("Enter option number or any number to stop");
                    int optSearch = scanner.nextInt();
                    if (optSearch == 1) {
                        System.out.println("Enter Author name");
                        String Author = scanner.next();
                        resultSearch = SearchBooksByAuthor(Author, resultSearch);
                        System.out.println("Done and Continue");
                    } else if (optSearch == 2) {
                        System.out.println("Enter Title name");
                        String Title = scanner.next();
                        resultSearch = SearchBooksByTitle(Title, resultSearch);
                        System.out.println("Done and Continue");
                    } else if (optSearch == 3) {
                        System.out.println("Enter ID ");
                        String ID = scanner.next();
                        Book result = SearchBooksByID(ID, resultSearch);
                        resultSearch.clear();
                        resultSearch.add(result);
                        System.out.println("Done and Continue");
                    } else if (optSearch == 4) {
                        GenreMenu();
                        String Genre = "";
                        while (true) {
                            System.out.println("Enter Genre Number:");
                            int n = scanner.nextInt();
                            if (n == 1) {
                                Genre = "Science";
                                break;
                            }
                            if (n == 2) {
                                Genre = "Fiction";
                                break;
                            }
                            if (n == 3) {
                                Genre = "Drama";
                                break;
                            } else System.out.println("please enter 1,2,3");
                        }
                        resultSearch = SearchBooksByGenre(Genre, resultSearch);
                        System.out.println("Done and Continue");
                    } else if (optSearch == 5) {
                        System.out.println("Enter Price");
                        double Price = scanner.nextDouble();
                        resultSearch = SearchBooksByPrice(Price, resultSearch);
                        System.out.println("Done and Continue");
                    } else if (optSearch == 6) {
                        String Publish_Date=null;
                        while (true) {
                            System.out.println("Enter Date in format MM/DD/yyyy");
                            String stringDate = scanner.next();
                            if (isValid(stringDate)) {
                                Publish_Date = stringDate;
                                break;
                            }
                        }
                        resultSearch = SearchBooksByPublishDate(Publish_Date, resultSearch);
                        System.out.println("Done and Continue");
                    } else if (optSearch == 7) {
                        System.out.println("Enter sub sequence from description");
                        String description = scanner.next();
                        resultSearch = SearchBooksByDescription(description, resultSearch);
                        System.out.println("Done and Continue");
                    } else {
                        if (resultSearch.size() > 0) {
                            System.out.println("Number of Books: " + resultSearch.size());
                            for (Book book : resultSearch) {
                                System.out.println(book);
                            }
                            break;
                        } else {
                            System.out.println("No Books");
                            break;
                        }
                    }
                }
            }
            else if (option == 3) {
                System.out.println("Enter Book ID");
                String ID = scanner.next();
                DeletedBook(ID);
            }
            else if (option == 4) {
                System.out.println("Enter Book ID");
                String ID = scanner.next();
                List<Book> ReadedBooks = ReadBooks();
                Book result = SearchBooksByID(ID, ReadedBooks);
                if (result!=null){
                    System.out.println(result);
                }
                else {
                    System.out.println("No Books");
                }
                String Author=null,Title=null,Genre=null,Description=null;
                double Price = 0.0;
                String Publish_Date = null;
                while (true){
                    System.out.println("Enter number for Attribute want change (if need stop change enter any number)");
                    AttributesMenu();

                    System.out.println("Enter option: ");
                    int opt = scanner.nextInt();
                    if(opt==1){
                        System.out.println("Author");
                         Author = scanner.next();
                        while(!Author.matches("[a-zA-Z]+")){
                            System.out.println("Must be char only");
                            System.out.println("Author");
                            Author = scanner.next();
                        }
                    }
                    else if(opt==2){
                        System.out.println("Title");
                         Title = scanner.next();
                    }
                    else if(opt==3){
                        GenreMenu();
                        while (true) {
                            System.out.println("Enter Genre Number:");
                            int n = scanner.nextInt();
                            if (n == 1) {Genre = "Science";break;}
                            if (n == 2) {Genre = "Fiction";break;}
                            if (n == 3) {Genre = "Drama";break;}
                            else System.out.println("please enter 1,2,3");
                        }
                    }
                    else if(opt==4){
                        System.out.println("Price");
                        Price = scanner.nextDouble();
                    }
                    else if(opt==5){
                        while (true) {
                            System.out.println("Enter Date in format MM/DD/yyyy");
                            String stringDate = scanner.next();
                            if (isValid(stringDate)) {
                                Publish_Date = stringDate;
                                break;
                            }
                        }

                    }
                    else if(opt==6){
                        System.out.println("Description");
                         Description = scanner.next();
                    }
                    else {
                        Book book = new Book(ID, Author, Title, Genre, Price, Publish_Date, Description);
                        UpdatedBook(ID,book);
                        break;
                    }
                }
            }
            else if(option==5){
                Comparator<Book> compareById = (Book o1, Book o2) ->
                        o1.getID().compareTo( o2.getID() );
                Comparator<Book> compareByAuthor = (Book o1, Book o2) ->
                        o1.getAuthor().compareTo( o2.getAuthor() );
                Comparator<Book> compareBytTitle= (Book o1, Book o2) ->
                        o1.getTitle().compareTo( o2.getTitle() );
                Comparator<Book> compareByGenre= (Book o1, Book o2) ->
                        o1.getGenre().compareTo( o2.getGenre() );
                Comparator<Book> compareByPublish_Date= (Book o1, Book o2) ->
                        o1.getPublish_Date().compareTo( o2.getPublish_Date() );
                while (true) {
                    List<Book> ReadedBooks = ReadBooks();
                    System.out.println("Sort->  Enter option number");
                    SortAttributesMenu();
                    int opt=scanner.nextInt();
                    if (opt == 1) {
                        TypeOfSortedMenu();
                        int optType = scanner.nextInt();
                        if (optType == 1) {
                            ReadedBooks.sort(compareByAuthor);
                        } else {
                            ReadedBooks.sort(compareByAuthor.reversed());
                        }

                        writeBook(ReadedBooks);
                    }
                    else if (opt == 2) {
                        TypeOfSortedMenu();
                        int optType = scanner.nextInt();
                        if (optType == 1) {
                            ReadedBooks.sort(compareBytTitle);
                        } else{
                            ReadedBooks.sort(compareBytTitle.reversed());
                        }
                        DeletedAllBook(ReadedBooks);
                        writeBook(ReadedBooks);
                    }
                    else if (opt == 3) {
                        TypeOfSortedMenu();
                        int optType = scanner.nextInt();
                        if (optType == 1) {
                            ReadedBooks.sort(compareByGenre);

                        } else {
                            ReadedBooks.sort(compareByGenre.reversed());
                        }
                        DeletedAllBook(ReadedBooks);
                        writeBook(ReadedBooks);
                    }
                    else if (opt == 4) {
                        TypeOfSortedMenu();
                        int optType = scanner.nextInt();
                        if (optType == 1) {
                            ReadedBooks.sort(compareById);
                        } else {
                            ReadedBooks.sort(compareById.reversed());
                        }
                        DeletedAllBook(ReadedBooks);
                        writeBook(ReadedBooks);
                    }
                    else if (opt == 5) {
                        TypeOfSortedMenu();
                        int optType = scanner.nextInt();
                        if (optType == 1) {
                            ReadedBooks.sort(compareByPublish_Date);
                        } else {
                            ReadedBooks.sort(compareByPublish_Date.reversed());
                        }
                        DeletedAllBook(ReadedBooks);
                        writeBook(ReadedBooks);
                    }
                    else{
                        break;
                    }
                }


            }
            else break;
        }
    }
}
