// Prefisso per l'API endpoint.
const API_URI = "http://localhost:8080";

// Funzione di utilità utile per stampare errori sulla console e come alert.
function onError(msg, error) {
    const out = `${msg}: ${error}`;
    console.error(out);
    alert(out);
}

// Effettua la chiamata GET "/keyboards" e restituisce l'elenco delle tastiere.
async function getKeyboards() {
    // WIP  
}

// Effettua la chiamata DELETE "/keyboards/{id}".
async function deleteKeyboard(id) {
    // WIP  
}

// Effettua la chiamata GET "/keyboards/{id}" e restituisce la tastiera dal JSON.
async function getKeyboard(id) {
    // WIP 
}

// Effettua la chiamata POST "/keyboards" e restituisce l'ID della tastiera creata.
async function postKeyboard(keyboard) {
    // WIP
}

// Effettua la chiamata POST "/keyboards/{id}/comments" e restituisce l'ID del commento creato.
async function postComment(id, content) {
    // WIP
}

// Effettua la chiamata GET "/keyboards/{keyboardId}/comments/{commentId}" e restituisce il commento dal JSON.
async function getComment(keyboardId, commentId) {
    // WIP
}

// Effettua la chiamata DELETE "/keyboards/{id}".
async function deleteComment(keyboardId, commentId) {
    // WIP
}