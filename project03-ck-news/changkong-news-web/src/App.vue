<script setup lang="ts">
import Header from "./components/Header.vue";
import {computed} from "vue";
import {useRoute} from "vue-router";

const route = useRoute()

const showHeader = computed(()=>{
    return route.path !="/login" && route.path !="/register";
})
</script>
<template>
    <div class="Apppage">
        <Header v-if="showHeader"/>
        <router-view />
    </div>
</template>

<style scoped>
.Apppage{
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 50px;
}
</style>
