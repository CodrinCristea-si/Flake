package com.example.GUIController;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import com.example.Controller.NewController.MainController;
import com.example.Domain.Message;
import com.example.Domain.User;
import com.example.Utils.Exceptions.Exception;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.w3c.dom.Document;

public class StatisticsGUIController {
    private MainController cont;
    private User user;//cel care are trimite cere

    public void setStatisticsGUIController(MainController cont, User user) {
        this.cont = cont;
        this.user = user;
    }

    ObservableList<PrintedStats1> model1 = FXCollections.observableArrayList();

    @FXML
    private TableView<PrintedStats1> tableStats1;
    //private TableView<PrintedStats1> tableStats2;
    @FXML
    private TextField textStart;
    @FXML
    private TextField textEnd;
    @FXML
    private TextField textUsername;
    @FXML
    private TextField textNo;

    @FXML
    private TableColumn<PrintedStats1,String> tableColumnUsername;
    @FXML
    private TableColumn<PrintedStats1, String> tableColumnNumb;

    @FXML
    private Label labelInner;

    @FXML
    public void initialize() {
        //tableStats1.setEditable(false);
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<PrintedStats1, String>("Username"));
        tableColumnNumb.setCellValueFactory(new PropertyValueFactory<PrintedStats1, String>("No_Messages"));
        tableStats1.setItems(model1);
    }

    private boolean Stat1=false;

    public void initModel1(){

        // System.out.println(cont.RequestsForAUser(user.getUsername()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Map<String,Integer> stat1=cont.getNoMessagesPerFriend(user.getUsername(), LocalDate.parse(textStart.getText(),formatter),LocalDate.parse(textEnd.getText(),formatter));
        System.out.println(stat1);
        model1.setAll(stat1.entrySet().stream()
                .map(x-> new PrintedStats1(x.getKey(),x.getValue().toString()))
                .collect(Collectors.toList()));
        textNo.setText(String.valueOf(cont.getNumberOfNewFriendsPeriod(user.getUsername(), LocalDate.parse(textStart.getText(),formatter),LocalDate.parse(textEnd.getText(),formatter))));
    }

    public void initModel2(){

        // System.out.println(cont.RequestsForAUser(user.getUsername()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Message> stat2=cont.getConvoInSF(user.getUsername(),textUsername.getText(), LocalDate.parse(textStart.getText(),formatter),LocalDate.parse(textEnd.getText(),formatter));
        model1.setAll(stat2.stream()
                .map(x-> new PrintedStats1(x.getMessage(),x.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"))))
                .collect(Collectors.toList()));
        textNo.setText(String.valueOf(stat2.size()));
    }

    public void handlegenStat1(ActionEvent ev){
        try{
            if(textStart.getText().isEmpty() || textEnd.getText().isEmpty())
                throw new Exception("The fields with the start date and the end date should not be empty!");
            else{
                Stat1=true;
                labelInner.setText("New friends this season:");
                tableColumnUsername.setText("Username");
                tableColumnNumb.setText("No_Messages");
                initModel1();
            }
        }
        catch(Exception e){
            textStart.setText("");
            textEnd.setText("");
            textUsername.setText("");
            MessageAlert.showErrorMessage(null,e.getDescription());
        }
    }

    public void handlegenStat2(ActionEvent ev){
        try{
            if(textStart.getText().isEmpty() || textEnd.getText().isEmpty() || textUsername.getText().isEmpty())
                throw new Exception("The fields with the start date and the end date and the username should not be empty!");
            else{
                Stat1=false;
                labelInner.setText("Total Messages:");
                tableColumnUsername.setText("Message");
                tableColumnNumb.setText("Date");
                initModel2();
            }
        }
        catch(Exception e){
            textStart.setText("");
            textEnd.setText("");
            textUsername.setText("");
            MessageAlert.showErrorMessage(null,e.getDescription());
        }
    }

    public void handleExport(ActionEvent ev) throws IOException {
        if(Stat1)exportStat1();
        else exportStat2();
    }

    public void exportStat1() throws IOException {
        String path="";
        JFileChooser j=new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x=j.showSaveDialog(null);

        if(x==JFileChooser.APPROVE_OPTION){
            path=j.getSelectedFile().getPath();
        }
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDRectangle rect = page.getMediaBox();
        document.addPage(page);
        PDPageContentStream cos = new PDPageContentStream(document, page);

        cos.beginText();
        cos.setFont(PDType1Font.TIMES_ROMAN, 12);
        cos.newLineAtOffset(25, page.getMediaBox().getHeight()-25);
        String text3= "User :"+user.getUsername();
        cos.showText(text3);
        cos.endText();

        cos.beginText();
        cos.setFont(PDType1Font.TIMES_ROMAN, 12);
        cos.newLineAtOffset(25, page.getMediaBox().getHeight()-50);
        String text1= "Time :"+textStart.getText()+" - "+textEnd.getText();
        cos.showText(text1);
        cos.endText();

        cos.beginText();
        cos.setFont(PDType1Font.TIMES_ROMAN, 12);
        cos.newLineAtOffset(25, page.getMediaBox().getHeight()-75);
        String text2= labelInner.getText()+" "+textNo.getText();
        cos.showText(text2);
        cos.endText();


        float margin = 25;
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 50;
        float yPosition = page.getMediaBox().getHeight()-100;
        BaseTable table = new BaseTable(yPosition, yStartNewPage,
                bottomMargin, tableWidth, margin, document, page, true, drawContent);

        Row<PDPage> headerRow = table.createRow(20);
        Cell<PDPage> cell1 = headerRow.createCell(50, tableColumnUsername.getText());
        cell1.setFont(PDType1Font.HELVETICA_BOLD);
        cell1.setAlign(HorizontalAlignment.CENTER);
        cell1.setFontSize(15);
        Cell<PDPage> cell2 = headerRow.createCell(50, tableColumnNumb.getText());
        cell2.setFont(PDType1Font.HELVETICA_BOLD);
        cell2.setAlign(HorizontalAlignment.CENTER);
        cell2.setFontSize(15);
        table.addHeaderRow(headerRow);
        for(var el:tableStats1.getItems()){
            Row<PDPage> row = table.createRow(20);
            Cell<PDPage>cell3 = row.createCell(50, el.getUsername());
            Cell<PDPage>cell4 = row.createCell(50, el.getNo_Messages());
            cell3.setFontSize(12);
            cell4.setFontSize(12);

        }
        table.draw();
        cos.close();
        //List<String>pieces=path.split
        document.save(path+"\\stat1.pdf");
        document.close();
    }



    public void exportStat2() throws IOException {
        String path="";
        JFileChooser j=new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x=j.showSaveDialog(null);

        if(x==JFileChooser.APPROVE_OPTION){
            path=j.getSelectedFile().getPath();
        }
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        PDRectangle rect = page.getMediaBox();
        document.addPage(page);
        PDPageContentStream cos = new PDPageContentStream(document, page);

        cos.beginText();
        cos.setFont(PDType1Font.TIMES_ROMAN, 12);
        cos.newLineAtOffset(25, page.getMediaBox().getHeight()-25);
        String text3= "Conversation between :"+user.getUsername() +" <--> "+textUsername.getText();
        cos.showText(text3);
        cos.endText();

        cos.beginText();
        cos.setFont(PDType1Font.TIMES_ROMAN, 12);
        cos.newLineAtOffset(25, page.getMediaBox().getHeight()-50);
        String text1= "Time :"+textStart.getText()+" - "+textEnd.getText();
        cos.showText(text1);
        cos.endText();

        cos.beginText();
        cos.setFont(PDType1Font.TIMES_ROMAN, 12);
        cos.newLineAtOffset(25, page.getMediaBox().getHeight()-75);
        String text2= labelInner.getText()+" "+textNo.getText();
        cos.showText(text2);
        cos.endText();


        float margin = 25;
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
        boolean drawContent = true;
        float yStart = yStartNewPage;
        float bottomMargin = 50;
        float yPosition = page.getMediaBox().getHeight()-100;
        BaseTable table = new BaseTable(yPosition, yStartNewPage,
                bottomMargin, tableWidth, margin, document, page, true, drawContent);

        Row<PDPage> headerRow = table.createRow(20);
        Cell<PDPage> cell1 = headerRow.createCell(50, tableColumnUsername.getText());
        cell1.setFont(PDType1Font.HELVETICA_BOLD);
        cell1.setAlign(HorizontalAlignment.CENTER);
        cell1.setFontSize(15);
        Cell<PDPage> cell2 = headerRow.createCell(50, tableColumnNumb.getText());
        cell2.setFont(PDType1Font.HELVETICA_BOLD);
        cell2.setAlign(HorizontalAlignment.CENTER);
        cell2.setFontSize(15);
        table.addHeaderRow(headerRow);
        for(var el:tableStats1.getItems()){
            Row<PDPage> row = table.createRow(20);
            Cell<PDPage>cell3 = row.createCell(50, el.getUsername());
            Cell<PDPage>cell4 = row.createCell(50, el.getNo_Messages());
            cell3.setFontSize(12);
            cell4.setFontSize(12);

        }
        table.draw();
        cos.close();
        //List<String>pieces=path.split
        document.save(path+"\\stat2.pdf");
        document.close();
    }
}
