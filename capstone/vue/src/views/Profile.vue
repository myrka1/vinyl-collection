<template>
  <div class="container">
    <main><library id="library" /></main>
    <aside>
      <div id="frame">
        <form @submit.prevent="submitSearchForm" class="form">
          <h3>Search for New Music</h3>
          <label for="artist">Artist:</label>
          <input type="text" id="artist" v-model="artist" />
          <br />
          <label for="album">Album:</label>
          <input type="text" id="album" v-model="album" />
          <br />
          <button id="form-button" type="submit">Search</button>
        </form>
        <div>
          <div
            v-for="record in stuff"
            v-bind:key="record.musicBrainzId"
            class="info"
          >
            <img :src="record.frontLink" class="album" />
            <p>
              <strong>{{ record.recordArtist }}</strong>
            </p>
            <p>
              <em>{{ record.recordTitle }}</em>
            </p>
            <button id="add-button" @click="addAlbum(record)">Add</button>
          </div>
        </div>
      </div>
    </aside>
  </div>
</template>

<script>
import service from "../services/ServerService.js";
import library from "../components/Library.vue";


export default {
  data() {
    return {
      artist: "",
      album: "",
      stuff: [],
    };
  },
  methods: {
    submitSearchForm() {
      try {
        service.recordList(this.artist, this.album).then((response) => {
          this.stuff = response.data;
          this.artist = "";
          this.album = "";
        });
      } catch (error) {
        //console.error(error);
      }
    },
    addAlbum(record) {
      service.addToLibrary(record);
      console.log("album added to library"); //debug line
      console.log(record);
      console.log("Record Title: " + record.recordTitle);
      setTimeout( () => {
        location.reload();
      }, 150);
    },
  },
  components: {
    library,
  },
};
</script>

<style scoped>
.album {
  height: 200px;
  width: 200px;
  background-color: black;
}
.container {
  display: grid;
  grid-template-columns: 3fr 1fr;
  height: 100%;
}
aside {
  display: inline-block;
  display: flex;
  padding: 80px;
  padding-top: 20px;
  margin: 20px;
  margin-top: 80px;
  align-items: center;
  flex-direction: column;
  background-color: #f1defa;
  margin-right: 200px;
}

#frame {
  display: inline-block;
  border: 5px solid #0e2e5b;
  border-radius: 10px;
}
form {
  display: flex;
  flex-direction: column;
  padding: 15px;
  text-align: center;
}
ul {
  display: flex;
  flex-direction: column;
  list-style-type: none;
  text-align: center;
  max-height: 500px;
  overflow-y: auto;
}
li {
  margin-bottom: 10px;
}
#form-button {
  background-color: #e3d26f;
  border-radius: 10px;
}

#form-button:hover,
#add-button:hover {
  filter: brightness(1.25);
}

#add-button {
  background-color: #78c3fb;
  border-radius: 10px;
}
h3 {
  margin-top: -5px;
}

.info {
  padding: 20px;
  text-align: center;
}
</style>