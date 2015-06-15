package de.hska.centurion.io;

import javax.xml.bind.JAXBException;

import com.sun.org.apache.xerces.internal.parsers.XMLParser;

import de.hska.centurion.domain.input.Results;

public class IOTest {

	public static void main(String[] args) throws JAXBException {
		Results results = XmlParser
				.parseXmlFile("C:\\Users\\Simon\\Desktop\\centurionTest1\\270_2_1result.xml");
	}
}
