<raw>
  <script>
    export default {
      setInnerHTML() {
        this.root.innerHTML = this.props.html
      },
      onMounted() {
        this.setInnerHTML()
      },
      onUpdated() {
        this.setInnerHTML()
      }
    }
  </script>
</raw>
