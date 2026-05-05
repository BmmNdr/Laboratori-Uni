package it.unimib.sd2026;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/keyboards")
public class KeyboardsServlet extends HttpServlet {

    private List<Keyboard> keyboards = new ArrayList<Keyboard>();
    private int lastId = 3;

    /**
     * Servlet initialization method.
     * Viene chiamato una sola volta dal container quando la servlet viene caricata.
     */
    @Override
    public void init() throws ServletException {
        super.init();

        var kb = new Keyboard();
        kb.setName("K55 RGB PRO");
        kb.setManufacturer("Corsair");
        kb.setYear(2023);
        kb.setErgonomic(true);
        kb.setBacklight(true);
        kb.setLayout(Keyboard.Layout.QWERTY);
        kb.setId(0);
        kb.addComment("Commento 1");
        kb.addComment("Commento 2");
        keyboards.add(kb);

        kb = new Keyboard();
        kb.setName("K30");
        kb.setManufacturer("Corsair");
        kb.setYear(2020);
        kb.setErgonomic(false);
        kb.setBacklight(false);
        kb.setLayout(Keyboard.Layout.QWERTY);
        kb.setId(1);
        keyboards.add(kb);

        kb = new Keyboard();
        kb.setName("G213");
        kb.setManufacturer("Logitech");
        kb.setYear(2023);
        kb.setErgonomic(false);
        kb.setBacklight(true);
        kb.setLayout(Keyboard.Layout.Dvorak);
        kb.setId(2);
        kb.addComment("Commento 5");
        kb.addComment("Commento 6");
        keyboards.add(kb);
    }

    /**
     * Metodo invocato da GET /keyboards.
     *
     * Restituisce al client una pagina HTML che mostra una tabella con le tastiere presenti.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rawId = request.getParameter("id");

        var buf = new StringBuilder();
        if(rawId == null){
            allTablePage(buf);
        }
        else {
            var id = 0;
            try {
                id = Integer.parseInt(rawId);

                Keyboard keyboardOfId = null;

                for (Keyboard keyboard : keyboards) {
                    if (keyboard.getId() == id){
                        keyboardOfId = keyboard;
                        break;
                    }
                }

                if (keyboardOfId == null){
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("404 Bad Request: ID Not Found.");
                }
                else
                    keyboardPage(keyboardOfId, buf);

            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("400 Bad Request: Invalid ID.");
            }
        }

        // Invio del corpo della risposta e content-type.
        response.setContentType("text/html");
        response.getWriter().append(buf.toString());
    }

    private void keyboardPage(Keyboard keyboard, StringBuilder buf){
        buf.append("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Keyboard Manager Panel</title>
                    </head>
                    <body>
                        <h1>Keyboard %s</h1>""".formatted(keyboard.getId()));

        buf.append("""
                <ul>
                <li><b>Name:</b>%s</li>
                <li><b>Manufacturer:</b>%s</li>
                <li><b>Year:</b>%s</li>
                <li><b>Ergonomic:</b>%s</li>
                <li><b>Backlight:</b>%s</li>
                <li><b>Layout:</b>%s</li>
                <li><b>Comments:</b><ul>
                """.formatted(keyboard.getName(), keyboard.getManufacturer(), keyboard.getYear(), keyboard.isErgonomic(), keyboard.isBacklight(), keyboard.getLayout()));
    
        ArrayList<String> comments = keyboard.getComments();

        if(comments.size() <= 0){
            buf.append("<li>No Comments</li>");
        } else {
            for (String comment : comments) {
                buf.append("<li>%s</li>".formatted(comment));
            }
        }

        buf.append("</ul></ul>");

        buf.append("<a href=/keyboards>Go back</a>");
    
        // Chiusura del body.
        buf.append("</body></html>");
    }

    private void allTablePage(StringBuilder buf){
        // Costruisce la tabella HTML a mano con le stringhe, salvando man mano il contenuto in un
        // buffer. ATTENZIONE: nel mondo reale si usano delle librerie che in automatico creano codice
        // HTML, ma nei nostri esercizi va bene anche fare a mano!
        buf.append("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Keyboard Manager Panel</title>
                    </head>
                    <body>
                        <h1>Keyboard Manager Panel</h1>""");

        // Header e apertura della tabella.
        buf.append("""
            <table>
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Name</th>
                        <th>Manufacturer</th>
                        <th>Year</th>
                        <th>Ergonomic</th>
                        <th>Backlight</th>
                        <th>Layout</th>
                        <th>Comments</th>
                    </tr>
                </thead>
                <tbody>
        """);

        synchronized(this){
            // Aggiunta di una riga per tastiera.
            for (Keyboard keyboard : keyboards) {
                String row = "<tr>";
                row += "<td>" + keyboard.getId() + "</td>";
                row += "<td>" + keyboard.getName() + "</td>";
                row += "<td>" + keyboard.getManufacturer() + "</td>";
                row += "<td>" + keyboard.getYear() + "</td>";
                row += "<td>" + keyboard.isErgonomic() + "</td>";
                row += "<td>" + keyboard.isBacklight() + "</td>";
                row += "<td>" + keyboard.getLayout() + "</td>";
                row += "<td><a href=/keyboards?id=%s>Click!</a></td>".formatted(keyboard.getId());
                row += "</tr>";
                buf.append(row);
            }
        }   

        // Chiusura della tabella.
        buf.append("</tbody></table>");

        // Modulo aggiunta nuova tastiera
        newKeyboardModule(buf);

        // Chiusura del body.
        buf.append("</body></html>");
    }

    private void newKeyboardModule(StringBuilder buf) {

        String layoutOptions = "";
        for (Keyboard.Layout l : Keyboard.Layout.values()) {
            layoutOptions += """
                    <option value="%s">%s</option>
                    """.formatted(l, l);
        }

        buf.append("""
            <form action="keyboards" method="POST">
                <p>
                <label for="name">Name:</label>
                <br>
                <input type="text" id="name" name="name" value="" required />
                </p>

                <p>
                <label for="manufacturer">Manufacturer:</label>
                <br>
                <input type="text" id="manufacturer" name="manufacturer" value="" required />
                </p>

                <p>
                <label for="year">Year:</label>
                <br>
                <input type="number" id="year" name="year" min="1900" max="2026" value="" required />
                </p>

                <p>
                <label for="ergonomic">Ergonomic:</label>
                <br>
                <input type="checkbox" id="ergonomic" name="ergonomic" value="true"/>
                </p>

                <p>
                <label for="backlight">Backlight:</label>
                <br>
                <input type="checkbox" id="backlight" name="backlight" value="true"/>
                </p>

                <p>
                <label for="layout">Layout:</label>
                <br>
                <select id="layout" name="layout">
                %s
                </select>
                </p>

                <p>
                <input type="submit" value="Submit">
                </p>
            </form>
        """.formatted(layoutOptions));
    }

    /**
     * Metodo invocato da POST /keyboards.
     *
     * Aggiunge una nuova tastiera nella lista di tastiere.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        String name = request.getParameter("name");
        String manufacturer = request.getParameter("manufacturer");
        int year = Integer.parseInt(request.getParameter("year"));
        Boolean isErgonomic = Boolean.parseBoolean(request.getParameter("ergonomic"));
        Boolean isBacklight = Boolean.parseBoolean(request.getParameter("backlight"));
        String layout = request.getParameter("layout");

        Keyboard kb = new Keyboard();
        kb.setName(name);
        kb.setManufacturer(manufacturer);
        kb.setYear(year);
        kb.setErgonomic(isErgonomic);
        kb.setBacklight(isBacklight);
        kb.setLayout(Keyboard.Layout.valueOf(layout));
        
        synchronized(keyboards) {
            kb.setId(lastId++);
            keyboards.add(kb);
        }

        response.sendRedirect("/keyboards");
        
    }
}