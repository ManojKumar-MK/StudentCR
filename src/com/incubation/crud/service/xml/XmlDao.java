package com.incubation.crud.service.xml;

import com.incubation.crud.Constants;
import com.incubation.crud.Student;
import com.incubation.crud.service.Dao;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlDao implements Dao {

    private  File f = new File(Constants.FILENAME_XML);
    @Override
    public void add(Student student) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        Element root = null;
        if(f.exists())

        {
            try {
                document = documentBuilder.parse(f);  // IF FILE EXISTS LOAD THAT IN DOCUMENT
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }

            assert document != null;

            root = document.getDocumentElement();   // If file Already exist get the ROOT <students>

        }
        else {
            assert documentBuilder != null;
            document = documentBuilder.newDocument();  // IF NOT CREATE NEW DOCUMENT AND ADD ELEMENT
            root = document.createElement("students");           // <students> </students>
            document.appendChild(root);

        }


        Element studentElement = document.createElement("student");
        studentElement.setAttribute("rollno",String.valueOf(student.getRollno()));
        root.appendChild(studentElement);   // <students><student rollno = "50"> </student> </students>

        Element nameElement = document.createElement("name");
        nameElement.appendChild(document.createTextNode(student.getName()));
        studentElement.appendChild(nameElement);  //<students><student rollno = "50"> <name>MK </name> </student> </students>

        Element courseElement = document.createElement("course");
        courseElement.appendChild(document.createTextNode(student.getCourse()));
        studentElement.appendChild(courseElement);

        Element addressElement = document.createElement("address");
        addressElement.appendChild(document.createTextNode(student.getAddress()));
        studentElement.appendChild(addressElement);

        Element phoneElement = document.createElement("phone");
        phoneElement.appendChild(document.createTextNode(student.getPhone()));
        studentElement.appendChild(phoneElement);


        /*
            THEN YOU NEED CREATE WRITE IN FILE USING DOMsource and TransformFactory,Transformer,StreamResult

         */

        writeXml(document);
        System.out.println("Data Added in XML... ");

    }

    @Override
    public List<Student> list() {

        ArrayList<Student> students = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            assert documentBuilder != null;
            document = documentBuilder.parse(new File(Constants.FILENAME_XML));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        assert document != null;
        NodeList nodeList = document.getElementsByTagName("student");
        //System.out.println(nodeList.getLength());
        for(int i=0;i<nodeList.getLength();i++)
        {
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;
                int rollno = Integer.parseInt(element.getAttribute("rollno"));

                String name = element.getElementsByTagName("name").item(0).getTextContent();

                String course = element.getElementsByTagName("course").item(0).getTextContent();

                String address = element.getElementsByTagName("address").item(0).getTextContent();

                String phone = element.getElementsByTagName("phone").item(0).getTextContent();

                students.add(new Student(rollno,name,course,address,phone));

            }
        }

        System.out.println("Data Retrieved from XML");

        return students;
    }

    @Override
    public void delete(int rollno) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(f);
        }
        catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        assert document != null;


        Node students = document.getFirstChild(); // <students>

        NodeList studentList = document.getElementsByTagName("student");
        for(int i=0;i<studentList.getLength();i++)
        {
            Node node= studentList.item(i);
            int rno = Integer.parseInt(node.getAttributes().getNamedItem("rollno").getTextContent());

            if(rollno == rno)
            {
                students.removeChild(node);
            }
        }


        writeXml(document);


    }

    @Override
    public int get(int rollno) {
        return 0;
    }

    @Override
    public void modify(int rollno, String... params) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(f);
        }
        catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        assert document != null;
        NodeList nodeList = document.getElementsByTagName("student");
        for(int i=0;i<nodeList.getLength();i++)
        {
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE)
            {

                int rno = Integer.parseInt(node.getAttributes().getNamedItem("rollno").getTextContent());

                if(rollno == rno)
                {


                    NodeList childNodes = node.getChildNodes();
                    for(int j=0;j<childNodes.getLength();j++)
                    {
                        Node childNode = childNodes.item(j);

                        if(childNode.getNodeType() == Node.ELEMENT_NODE)
                        {

                            if("address".equals(childNode.getNodeName()))
                            {
                                childNode.setTextContent(params[0]);
                            }
                            if("phone".equals(childNode.getNodeName()))
                            {
                                childNode.setTextContent(params[1]);
                            }
                        }


                    }


                }
            }
        }

        writeXml(document);
        System.out.println("Data Modified in XML... ");


    }


    @Override
    public Student getRecord(int rollno) {

        Student student= null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(f);
        }
        catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        NodeList nodeList = document.getElementsByTagName("student");
        for(int i=0;i<nodeList.getLength();i++)
        {
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE)
            {

                Element element = (Element) node;
                int rno = Integer.parseInt(element.getAttribute("rollno"));

                if(rno == rollno)
                {
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String course = element.getElementsByTagName("course").item(0).getTextContent();
                    String address = element.getElementsByTagName("address").item(0).getTextContent();
                    String phone = element.getElementsByTagName("phone").item(0).getTextContent();
                    student = new Student(rno,name,course,address,phone);
                    break;
                }
            }
        }

        return student;


    }

    public void writeXml(Document document)
    {   

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = null;
        try {
            streamResult = new StreamResult(new FileWriter(f,false)); // append data
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            transformer.transform(domSource,streamResult); // Now the data Write to xml file from DOM
        } catch (TransformerException e) {
            e.printStackTrace();
        }
//
//        StreamResult streamResulConsole = new StreamResult(System.out);
//        try {
//            transformer.transform(domSource,streamResulConsole);
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }


    }
}
