package integrazione.control;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import control.RegistrazioneServlet;
import model.bean.GestoreBean;
import model.bean.GiocatoreBean;
import model.bean.StrutturaBean;
import model.dao.GestoreDAO;
import model.dao.GiocatoreDAO;
import model.dao.StrutturaDAO;

public class TestIntegrazioneRegistrazione {
	@Mock
	private HttpServletRequest req;
	
	@Mock
	private HttpServletResponse res;
	
	@Mock
	private ServletContext ctx;
	
	@Mock
	private HttpSession session;
	
	@Mock
	private RequestDispatcher rd;
	
	@Mock
	GiocatoreDAO gioDao = new GiocatoreDAO();
	
	@Mock
	GestoreDAO gesDao = new GestoreDAO();
	
	@Mock
	StrutturaDAO sDao = new StrutturaDAO();
	
	private RegistrazioneServlet servlet;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		servlet= new RegistrazioneServlet();
		when(req.getSession()).thenReturn(session);
	}
	
	@Test
	public void registrazioneGiocatoreOk() throws ServletException, IOException, SQLException {
		when(req.getParameter("cf")).thenReturn("giocatore");
		
		when(req.getParameter("nome")).thenReturn("pino");
		when(req.getParameter("cognome")).thenReturn("pino");
		when(req.getParameter("email")).thenReturn("cpinoi@pino.it");
		when(req.getParameter("username")).thenReturn("ukipino");
		when(req.getParameter("password")).thenReturn("pino");
		when(req.getParameter("nazione")).thenReturn("italia");
		when(req.getParameter("provincia")).thenReturn("napoli");
		when(req.getParameter("citta")).thenReturn("napoli");
		when(req.getParameter("cap")).thenReturn("80050");
		when(req.getParameter("telefono")).thenReturn("5433453443");
		when(req.getParameter("data")).thenReturn("2000-06-06");

		GiocatoreBean g = new GiocatoreBean();
		g.setUsername("ukipino");
		g.setEmail("cpinoi@pino.it");
		g.setNome("pino");
		g.setCognome("pino");
		g.setPassword("pino");
		g.setTelefono("5433453443");
		g.setDataNascita(Date.valueOf("2000-06-06"));
		g.setNazioneResidenza("Italia");
		g.setProvinciaResidenza("napoli");
		g.setCittaResidenza("napoli");
		g.setCapResidenza("80050");
		g.setValutazione(0);
		when(session.getAttribute("giocatore")).thenReturn(g);
		when(req.getRequestDispatcher(res.encodeRedirectURL("./PartecipaEventi.jsp"))).thenReturn(rd);
		servlet.doPost(req, res);
		verify(rd).forward(req, res);
		
		assertEquals(g, session.getAttribute("giocatore"));
		GiocatoreDAO gdp=new GiocatoreDAO();
		gdp.doDelete(g.getEmail());
	}
	
	@Test
	public void registrazioneGiocatoreNo() throws ServletException, IOException, SQLException {
		when(req.getParameter("cf")).thenReturn("giocatore");
		
		when(req.getParameter("nome")).thenReturn("pino");
		when(req.getParameter("cognome")).thenReturn("pino");
		when(req.getParameter("email")).thenReturn("pino@pino.it");
		when(req.getParameter("username")).thenReturn("pino");
		when(req.getParameter("password")).thenReturn("pino");
		when(req.getParameter("nazione")).thenReturn("italia");
		when(req.getParameter("provincia")).thenReturn("napoli");
		when(req.getParameter("citta")).thenReturn("napoli");
		when(req.getParameter("cap")).thenReturn("80050");
		when(req.getParameter("telefono")).thenReturn("5433453443");
		when(req.getParameter("data")).thenReturn("2000-06-06");
		
		GiocatoreBean g = new GiocatoreBean();
		g.setUsername("ukpino");
		g.setEmail("pino@pino.it");
		g.setNome("pino");
		g.setCognome("pino");
		g.setPassword("pino");
		g.setTelefono("5433453443");
		g.setDataNascita(Date.valueOf("2000-06-06"));
		g.setNazioneResidenza("Italia");
		g.setProvinciaResidenza("napoli");
		g.setCittaResidenza("napoli");
		g.setCapResidenza("80050");
		g.setValutazione(0);
		
		when(req.getRequestDispatcher(res.encodeRedirectURL("./Registrazione.jsp"))).thenReturn(rd);
		servlet.doPost(req, res);
		verify(rd).forward(req, res);
	}
	
	@Test
	public void registrazioneGestoreOk() throws ServletException, IOException, SQLException {
		when(req.getParameter("cf")).thenReturn("gestore");
		
		when(req.getParameter("emailG")).thenReturn("tgino@gino.it");
		when(req.getParameter("telefonoGestore")).thenReturn("5433453443");
		when(req.getParameter("nomeG")).thenReturn("gino");
		when(req.getParameter("cognomeG")).thenReturn("rossi");
		when(req.getParameter("strutturaG")).thenReturn("ginol");
		when(req.getParameter("nazioneG")).thenReturn("italia");
		when(req.getParameter("provinciaG")).thenReturn("napoli");
		when(req.getParameter("cittaG")).thenReturn("napoli");
		when(req.getParameter("capG")).thenReturn("80050");
		when(req.getParameter("indirizzoG")).thenReturn("via gino, 22");
		when(req.getParameter("telefonoStruttura")).thenReturn("3213453443");
		when(req.getParameter("passwordG")).thenReturn("tgino");
		
		
		GestoreBean g = new GestoreBean();
		StrutturaBean s = new StrutturaBean();
		
		g.setEmail("tgino@gino.it");
		g.setNome("gino");
		g.setCognome("rossi");
		g.setPassword("tgino");
		g.setTelefono("5433453443");
		g.setStruttura("ginol");
		
		s.setNome("ginol");
		s.setCap("80050");
		s.setCitta("napoli");
		s.setIndirizzo("via gino, 22");
		s.setNazione("italia");
		s.setProvincia("napoli");
		s.setTelefono("3213453443");

		when(session.getAttribute("gestore")).thenReturn(g);
		when(req.getRequestDispatcher(res.encodeRedirectURL("./RichiesteEventi.jsp"))).thenReturn(rd);
		servlet.doPost(req, res);
		verify(rd).forward(req, res);
		assertEquals(g, session.getAttribute("gestore"));
		GestoreDAO gDao = new GestoreDAO ();
		StrutturaDAO stDao = new StrutturaDAO ();
		gDao.doDelete(g.getEmail());
		stDao.doDelete(s.getNome());
	}
	
	@Test
	public void registrazioneGestoreNo() throws ServletException, IOException {
		when(req.getParameter("cf")).thenReturn("gestore");
		
		when(req.getParameter("emailG")).thenReturn("gino@gino.it");
		when(req.getParameter("telefonoGestore")).thenReturn("5433453443");
		when(req.getParameter("nomeG")).thenReturn("gino");
		when(req.getParameter("cognomeG")).thenReturn("rossi");
		when(req.getParameter("strutturaG")).thenReturn("playk");
		when(req.getParameter("nazioneG")).thenReturn("italia");
		when(req.getParameter("provinciaG")).thenReturn("napoli");
		when(req.getParameter("cittaG")).thenReturn("napoli");
		when(req.getParameter("capG")).thenReturn("80050");
		when(req.getParameter("indirizzoG")).thenReturn("via gino, 22");
		when(req.getParameter("telefonoStruttura")).thenReturn("3213453443");
		when(req.getParameter("passwordG")).thenReturn("tgino");
	
		GestoreBean g = new GestoreBean();
		StrutturaBean s = new StrutturaBean();
		
		g.setEmail("gino@gino.it");
		g.setNome("gino");
		g.setCognome("rossi");
		g.setPassword("tgino");
		g.setTelefono("5433453443");
		g.setStruttura("ginol");
		
		s.setNome("playo");
		s.setCap("80050");
		s.setCitta("napoli");
		s.setIndirizzo("via gino, 22");
		s.setNazione("italia");
		s.setProvincia("napoli");
		s.setTelefono("3213453443");
		
		when(req.getRequestDispatcher(res.encodeRedirectURL("./Registrazione.jsp"))).thenReturn(rd);
		servlet.doPost(req, res);
		verify(rd).forward(req, res);
	}
	
	@Test
	public void unitTest() throws ServletException, IOException, SQLException {
		servlet.gdt=gioDao;
		when(req.getParameter("cf")).thenReturn("giocatore");
		
		when(req.getParameter("nome")).thenReturn("pino");
		when(req.getParameter("cognome")).thenReturn("pino");
		when(req.getParameter("email")).thenReturn("cpinoi@pino.it");
		when(req.getParameter("username")).thenReturn("ukipino");
		when(req.getParameter("password")).thenReturn("pino");
		when(req.getParameter("nazione")).thenReturn("italia");
		when(req.getParameter("provincia")).thenReturn("napoli");
		when(req.getParameter("citta")).thenReturn("napoli");
		when(req.getParameter("cap")).thenReturn("80050");
		when(req.getParameter("telefono")).thenReturn("5433453443");
		when(req.getParameter("data")).thenReturn("2000-06-06");

		GiocatoreBean g = new GiocatoreBean();
		g.setUsername("ukipino");
		g.setEmail("cpinoi@pino.it");
		g.setNome("pino");
		g.setCognome("pino");
		g.setPassword("pino");
		g.setTelefono("5433453443");
		g.setDataNascita(Date.valueOf("2000-06-06"));
		g.setNazioneResidenza("Italia");
		g.setProvinciaResidenza("napoli");
		g.setCittaResidenza("napoli");
		g.setCapResidenza("80050");
		g.setValutazione(0);
		
		when(gioDao.doRetrieveByKey(Mockito.anyString())).thenReturn(null).thenReturn(g);
		when(gioDao.doRetrieveByUsername(Mockito.anyString())).thenReturn(null);
		when(session.getAttribute("giocatore")).thenReturn(g);
		when(req.getRequestDispatcher(res.encodeRedirectURL("./PartecipaEventi.jsp"))).thenReturn(rd);
		servlet.doPost(req, res);
		verify(rd).forward(req, res);
		
		assertEquals(g, session.getAttribute("giocatore"));
	}
	@Test
	public void unitTest2() throws ServletException, IOException, SQLException {
		servlet.sdt=sDao;
		servlet.gedt=gesDao;
        when(req.getParameter("cf")).thenReturn("gestore");
		
		when(req.getParameter("emailG")).thenReturn("tgino@gino.it");
		when(req.getParameter("telefonoGestore")).thenReturn("5433453443");
		when(req.getParameter("nomeG")).thenReturn("gino");
		when(req.getParameter("cognomeG")).thenReturn("rossi");
		when(req.getParameter("strutturaG")).thenReturn("ginol");
		when(req.getParameter("nazioneG")).thenReturn("italia");
		when(req.getParameter("provinciaG")).thenReturn("napoli");
		when(req.getParameter("cittaG")).thenReturn("napoli");
		when(req.getParameter("capG")).thenReturn("80050");
		when(req.getParameter("indirizzoG")).thenReturn("via gino, 22");
		when(req.getParameter("telefonoStruttura")).thenReturn("3213453443");
		when(req.getParameter("passwordG")).thenReturn("tgino");
		
		
		GestoreBean g = new GestoreBean();
		StrutturaBean s = new StrutturaBean();
		
		g.setEmail("tgino@gino.it");
		g.setNome("gino");
		g.setCognome("rossi");
		g.setPassword("tgino");
		g.setTelefono("5433453443");
		g.setStruttura("ginol");
		
		s.setNome("ginol");
		s.setCap("80050");
		s.setCitta("napoli");
		s.setIndirizzo("via gino, 22");
		s.setNazione("italia");
		s.setProvincia("napoli");
		s.setTelefono("3213453443");
		//mariolone
		when(gesDao.doRetrieveByKey(Mockito.anyString())).thenReturn(null).thenReturn(g);
		when(sDao.doRetrieveByKey(Mockito.anyString())).thenReturn(null).thenReturn(s);
		
		when(session.getAttribute("gestore")).thenReturn(g);
		when(req.getRequestDispatcher(res.encodeRedirectURL("./RichiesteEventi.jsp"))).thenReturn(rd);
		servlet.doPost(req, res);
		verify(rd).forward(req, res);
		assertEquals(g, session.getAttribute("gestore"));
	}
}
