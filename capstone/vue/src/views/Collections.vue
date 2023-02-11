<template>
  <div id="stuff">
  <h1>Collections:</h1>
    <div class="collection-container" v-for="collection in collections" v-bind:key="collection.collectionId">
      <div id="collection-text"><p id="title">{{collection.collectionTitle}}</p><p id="notes"> {{collection.notes}}</p></div>
      <Carousel v-bind:collection="collection" />
    </div>
  </div>
</template>

<script>
import Carousel from '../components/Carousel.vue'
import service from '../services/ServerService.js'

export default {
  data() {
    return {
      collections: [],
    }
  },
  created() {
    service.getAllCollections().then(
      (response) => {
        this.collections = response.data;
      }
    )
  },
  
  components: {
    Carousel
  }
}
</script>

<style scoped>
#stuff {
  padding: 0 20px;
}

#title {
  font-size: 30px;
  color: #0E2E5B
}

.collection-container {
  /* background: #0E2E5B; */
  padding: 5px;
  margin: 5px;
  width: 40%;
}

#title {
  color: #0E2E5B;
  font-weight: bold;
}

#notes {
  color: #0E2E5B
}

#collection-text{
  display: flex;
  align-items: center;
}

h1 {
  font-size: 3em;
  color: #0E2E5B;
  line-height: 20px;
}
</style>