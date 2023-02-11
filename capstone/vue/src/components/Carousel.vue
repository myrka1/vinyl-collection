<template>
<div id="Carousel">
    <button class="carousel-button" id="back-btn" v-on:click="back()">Back</button>
  <div class="viewing-window">
      <div class="inner" ref="inner" v-bind:style="innerStyles">
              <Record
              v-for="record in records" 
              v-bind:key="record.recordId"
              v-bind:record="record" 
              />
      </div>
  </div>
    <button class="carousel-button" id="next-btn" v-on:click="next()">Next</button>
  </div>
</template>

<script>
import service from '../services/ServerService.js';
import Record from '../components/Record.vue';

export default {
    props: ["collection"],
    data() {
        return {
            //Dummy Data has been placed inside for reference
            //If album order matters, place the last album at the front of the records array

            // records: [
            //     "Album 10", 
            //     "Album 1", 
            //     "Album 2", 
            //     "Album 3", 
            //     "Album 4", 
            //     "Album 5", 
            //     "Album 6", 
            //     "Album 7", 
            //     "Album 8", 
            //     "Album 9"
            //     ],
            records: [],
            innerStyles: {},
            step: '',
            transitioning: false,
        }
    },

    created() {
        service.getAllCollectionRecords(this.collection.collectionId).then(
            (response) => {
                this.records = response.data
            }
        )
    },

    components: {
        Record,
    },
    //Used for setting the position for steps/transitions to begin from, as well as set the distance of a step
    mounted() {
        window.setTimeout(()=>{
            this.setStep()
            this.resetTransition()
        }, 75)
      
    },

    methods: {
        //Sets the distance of a "step", which are the increments in which the records will shift left or right in the viewing-port
        setStep() {
            const innerWidth = this.$refs.inner.scrollWidth
            const totalRecords = this.records.length
            this.step = `${innerWidth / totalRecords}px`
        },

        /* Moves the inner-portion of the carousel to the left when the 'next' button is clicked.
        Reveals the next record in the current lineup, and crops the leftmost record of the current lineup */
        stepLeft() {
            this.innerStyles = {
                transform: `translateX(-${this.step})
                            translateX(-${this.step})`
            }
        },

        next() {
            if(this.transitioning) return
            this.transitioning = true

            this.stepLeft()

            this.afterTransition(() => {
                const record = this.records.shift()
                this.records.push(record)
                this.resetTransition()
                this.transitioning = false
            })
        },

         /* Moves the inner-portion of the carousel to the right when the 'back' button is clicked.
        Reveals the previous record in the lineup, and crops the rightmost record of the current lineup */

        stepRight() {
            this.innerStyles = {
                transform: `translateX(${this.step})
                            translateX(-${this.step})`
            }
        },

        
        back() {
            if(this.transitioning) return
            this.transitioning = true

            this.stepRight()

            this.afterTransition(() => {
                const record = this.records.pop()
                this.records.unshift(record)
                this.resetTransition()
                this.transitioning = false
            })
        },


        // These methods ensure that the transition's position is reset, and steps can be repeated per button push
        afterTransition (callback) {
            const listener = () => {
                callback()
                this.$refs.inner.removeEventListener('transitionend', listener)
            }
            this.$refs.inner.addEventListener('transitionend', listener)
        },

        resetTransition () {
            this.innerStyles = {
                transition: 'none',
                transform: `translateX(-${this.step})`
            }
        }
    }

}
</script>

<style scoped>
/* Affects the "viewing window" of the Carousel. Allows for the displaying of a certain number of records, and crops the rest that are out of view*/
.viewing-window {
    width: 100%;
    overflow: hidden;
}

/* Affects the container of the records shifting in and out of the .carousel container / "Viewing Window"
"White-space" keeps all records in a single row by preventing inline-block elements from wrapping once the parent container is filled, so it should remain untouched. 
"Transition" affects the speed of the transition */
.inner {
    transition: transform 0.5s;
    white-space: nowrap;
}


/* Affects the display properties of each record. Default values could be set here for when an image can't be loaded*/
.record {
    width: 200px;
    margin-right:10px;
    display: inline-flex;

    height: 200px;
    /*background-color: #0E2E5B;*/
    color: white;
    border-radius: 4px;
    align-items: center;
    justify-content: center;
    filter: brightness(1);
    transition: 0.3s;
}

.record:hover {
    filter: brightness(1.5);
}

/* Affects the display properties of the buttons*/ 
.carousel-button {
    display: flex;
    align-items: center;
    justify-content: center;
}

#next-btn {
    margin-left: 5px;
}

#back-btn {
    margin-right: 5px;
}

#Carousel {
    display: flex;
    justify-content: flex-start;
}

img.record {
    position: absolute;
    opacity: 1;
    margin-left: 10px;
    z-index: -5;
}

</style>





<!--Research/Method Guidance and Credit:Luis Velásquez https://dev.to/luvejo/how-to-build-a-carousel-from-scratch-using-vue-js-4ki0 -->