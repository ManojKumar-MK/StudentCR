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

    @Override
    public void add(Student student) {

        File f = new File(Constants.FILENAME_XML);
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

        /*
            THEN YOU NEED CREATE WRITE IN FILE USING DOMsource and TransformFactory,Transformer,StreamResult

         */
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        assert transformer != null;
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

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

        StreamResult streamResulConsole = new StreamResult(System.out);
        try {
            transformer.transform(domSource,streamResulConsole);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

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

                String name = element.getElementsByTagName("name").item(0).getChildNodes().item(0).getNodeValue();

                String course = element.getElementsByTagName("course").item(0).getChildNodes().item(0).getNodeValue();

                String address = element.getElementsByTagName("address").item(0).getChildNodes().item(0).getNodeValue();

                students.add(new Student(rollno,name,course,address));

            }
        }

        System.out.println("Data Retrieved from XML");

        return students;
    }
}
