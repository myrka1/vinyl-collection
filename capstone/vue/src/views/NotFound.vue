<template>
  <div id="not-found-splash">
      4<img src="../assets/broken-vinyl-icon.png" alt="a slowly rotating vinyl record" id="broken-record">4
  </div>
</template>

<script>

export default {}
</script>

<style scoped>
#not-found-splash {
    font-size: 16em;
    text-align: center;
}

#broken-record {
  width: 180px;
  height: 180px;
  border-radius: 20px;
  animation-name: spin;
  animation-duration: 1000ms;
  animation-iteration-count: infinite;
  animation-timing-function: ease-in-out;
  animation-direction: alternate;
  border-radius: 50%;
}

@keyframes spin {
  from {
    transform: rotate(45deg);
  }
  to {
    transform: rotate(75deg);
  }
}
</style>