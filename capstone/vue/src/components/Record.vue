<template>
  <div class="record">
      <img :src="record.frontLink" class="album" >
</div>
</template>

<script>
export default {
    props: ["record"]

}
</script>

<style scoped>

/* Affects the display properties of each record. Default values could be set here for when an image can't be loaded*/
.record {
    width: 200px;
    margin-right:10px;
    display: inline-flex;

    height: 200px;
    background-color: #0E2E5B;
    color: white;
    border-radius: 4px;
    align-items: center;
    justify-content: center;
    filter: brightness(1);
    transition: 0.3s;
}

.record:hover {
    filter: brightness(1.75);
}

img.record {
    position: absolute;
    opacity: 1;
    margin-left: 10px;
    z-index: -5;
}

#title {
    size: 10px;
    display: flex
}

#artist {
    size: 10px;
    display: flex
}

.album{
  height: 200px;
  width: 200px;
}

</style>