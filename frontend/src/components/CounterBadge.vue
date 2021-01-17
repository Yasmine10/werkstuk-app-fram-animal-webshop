<template>
  <span v-if="count > 0"  class="badge">
    {{ count }}
  </span>
</template>

<script>
export default {
  name: "CounterBadge",
  props: ['count']
}
</script>

<style scoped>
.badge {
  background-color: #484848;
  color: #F2F2F2;
  font-weight: bold;
  font-size: 14px;
  border-radius: 100%;
  width: 25px;
  height: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: -0.5rem;
  right: -1.3rem;
}
</style>