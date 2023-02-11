import axios from 'axios';

export default {
    getLibrary() {
        return axios.get("/library")
    },
    getAllCollectionRecords(collectionId) {
        return axios.get(`/findRecordsInCollection/${collectionId}`)
    },
    getCollection(id) {
        return axios.get(`/findCollection/${id}`)
    },
    getAllCollections() {
        return axios.get("/collections")
    },
    removeFromLibrary() {
        return axios.delete("")
    },
    removeFromCollection() {
        return axios.delete("deleteRecordFromCollection")
    },
    removeCollection() {
        return axios.delete("/deleteCollection")
    },
    addToLibrary(record) {
        return axios.post("/add", record);
    },
    addToCollection() {
        return axios.post("addRecordToCollection")
    },
    addNote() {
        return axios.post()
    },
    updateNote() {
        return axios.put()
    },
    updateCollectionInfo() {
        return axios.put()
    },
    recordList(artist, album) {
        const encodedArtist = encodeURIComponent(artist);
        const encodedAlbum = encodeURIComponent(album);
        return axios.get(`/test/${encodedArtist}/${encodedAlbum}`);
    },
    getUsername(){
        return axios.get('/currentUser');
    },
}
