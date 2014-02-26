package managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import models.Cadeira;
import models.PlanoDeCurso;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Gerenciador criado seguindo o padrão Pure Fabrication.
 */
public class GerenciadorDeCadeiras {

	/* 
	 * PADRÃO DE PROJETO: CREATOR - Um gerenciador de cadeiras precisa do
	 * mapas de cadeiras contendo todas as cadeiras do curso
	 */
	private static Map<String, Cadeira> listaDeCadeiras = new HashMap<String, Cadeira>();
	private static Map<String, Cadeira> cadeiraPrimeiro = new HashMap<String, Cadeira>();

	/*
	 * PADRÃO DE PROJETO: CONTROLLER - essa classe é responsável por
	 * controlar a adição de cadeiras no mapa.
	 */
	/**
	 * 
	 */
	private static void populaMapas() {
		Map<String, Cadeira> cadeirasPorId = new HashMap<String, Cadeira>();
		try {
			Document doc = criaParserXml();
			NodeList nList = doc.getElementsByTagName("cadeira");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					criaCadeiras(cadeirasPorId, nNode);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria a cadeira e insere no mapa de acordo com seus atributos descritos no
	 * xml de {@code nNode}.
	 */
	private static void criaCadeiras(Map<String, Cadeira> cadeirasPorId,
			Node nNode) {
		Cadeira criandoCadeira = new Cadeira();
		Element cadeiraXml = (Element) nNode;
		String idCadeira = cadeiraXml.getAttribute("id");
		String nomeCadeira = cadeiraXml.getAttribute("nome");
		int dificuldade = Integer.parseInt(cadeiraXml.getElementsByTagName("dificuldade").item(0).getTextContent());
		int creditos = Integer.parseInt(cadeiraXml.getElementsByTagName("creditos").item(0).getTextContent());
		int periodo = Integer.parseInt(cadeiraXml.getElementsByTagName("periodo").item(0).getTextContent());
		NodeList requisitos = cadeiraXml.getElementsByTagName("id");
		
		//SETANDO OS ATRIBUTOS DA CADEIRA
		criandoCadeira.setCreditos(creditos);
		criandoCadeira.setDificuldade(dificuldade);
		criandoCadeira.setNome(nomeCadeira);
		criandoCadeira.setPeriodo(periodo);
		for (int i = 0; i < requisitos.getLength(); i++) {
			criandoCadeira.addPreRequisito(cadeirasPorId.get(requisitos.item(i).getTextContent()));
		}
		
		cadeirasPorId.put(idCadeira, criandoCadeira);
		if (periodo == PlanoDeCurso.PRIMEIRO_PERIODO) {
			cadeiraPrimeiro.put(criandoCadeira.getNome(), criandoCadeira);
		} else {
			listaDeCadeiras.put(criandoCadeira.getNome(), criandoCadeira);
		}
	}

	/**
	 * Cria um parser XML
	 */
	private static Document criaParserXml()
			throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File("cadeiras.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	public static Map<String, Cadeira> getCadeirasPrimeiro() {
		if (listaDeCadeiras.isEmpty()) {
			populaMapas();
		}
		return cadeiraPrimeiro;
	}

	public static Map<String, Cadeira> getMapaDeCadeiras() {
		if (listaDeCadeiras.isEmpty()) {
			populaMapas();
		}
		return listaDeCadeiras;
	}
}
