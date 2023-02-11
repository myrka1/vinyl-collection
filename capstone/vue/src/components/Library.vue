<template>
  <div id="library">
    <h1>{{ username }}'s library</h1>

    <div
      v-for="album in library"
      v-bind:key="album.recordTitle"
      class="album-holder"
      v-on:mouseover="flip(album.musicBrainzId, album.backLink)"
      v-on:mouseout="unflip(album.musicBrainzId, album.frontLink)"
    >
    <img v-bind:src="album.backLink" class="hidden" />
      <img
        v-bind:src="album.frontLink"
        class="album"
        v-bind:id="album.musicBrainzId"
      />
      
    </div>
  </div>
</template>

<script>
import service from "../services/ServerService.js";

export default {
  data() {
    return {
      library: [],
      username: "",
    };
  },
  methods: {
    flip(id, back) {
      console.log("over");
      console.log(id);
      const album = document.getElementById(id);
      album.classList.remove("unflipped");
      album.classList.add("flipped");
      setTimeout(() => {
        album.setAttribute("src", back);
      }, 150);
    },
    unflip(id, front) {
      console.log("out");
      const album = document.getElementById(id);
      album.classList.remove("flipped");
      album.classList.add("unflipped");
      setTimeout(() => {
        album.setAttribute("src", front);
      }, 150);
    },
  },
  created() {
    service.getUsername().then((response) => {
      this.username = response.data;
      console.log(response.data);
    });
    service.getLibrary().then((response) => {
      this.library = response.data;
      console.table(response.data);
    });
  },
};
</script>

<style scoped>
#library {
  display: flex;
  flex-wrap: wrap;
  color:  #0E2E5B;
  gap: 10px;
  padding: 10px;
  border-radius: 10px;
  width: 1100px;
}
.album-holder {
  text-align: center;
  height: 200px;
  width: 200px;
}
.album {
  height: 200px;
  width: 200px;
  border-radius: 5px;
  filter: drop-shadow(5px 5px 0.4rem black);
}

h1 {
  position: block;
  width: 100%;
  color: #0E2E5B;
}

.flipped {
  animation-duration: 0.3s;
  animation-name: flip;
}

@keyframes flip {
  0% {
    width: default;
  }

  40% {
    width: 15px;
  }

  50% {
    width: 5px;
  }

  100% {
    width: default;
  }
}

.unflipped {
  animation-duration: 0.3s;
  animation-name: unflip;
}

@keyframes unflip {
  0% {
    width: default;
  }

  40% {
    width: 15px;
  }

  50% {
    width: 1px;
  }

  100% {
    width: default;
  }
}

.hidden {
  position: fixed;
  opacity: 0;
  height: 1px;
  width: 1px;
  bottom: 0px;
}
</style>