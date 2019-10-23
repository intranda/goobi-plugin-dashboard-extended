<rssfeed>
	<div class="box-content" if={props.show != 'down'}>
		<div if={state.feed.length == 0} class="alert alert-info alert-dismissable margin-bottom-10">
			{msg('dashboard_rssNotLoaded')}
		</div>
		
		<div class="padding:10px;display:block;" if={ state.feed.length != 0 }>
			<div each={(entry, idx) in state.feed} styleClass="margin-sides-10 block">
	
				<div if={idx != 0}>
					<hr style="margin: 10px" />
				</div>
	
				<div class="row">
					<div class="col-sm-12">
						<a href="{entry.link}" target="_blank">
							<raw html={entry.title} />
						</a>
					</div>
				</div>
	
				<div class="row margin-top-more">
					<div class="col-sm-12">
						<raw html={entry.description} />
					</div>
				</div>
			</div>
		</div>
	</div>
<script>
import Raw from './raw.tag'
 
export default {
    components: {
        Raw
    },
    onBeforeMount(props, state) {
      console.log(props)
      this.state = {
      	  feed: []
      };
      if(props.show != 'down') {
	      fetch(`/goobi/plugins/exdashboard/rssfeed`).then(resp => {
	        resp.json().then(json => {
	          console.log("got feed: " + json)
	          this.state.feed = json;
	          this.update();
	        })
	      });
      }
    },
    onMounted(props, state) {
      
    },
    onBeforeUpdate(props, state) {
      
    },
    onUpdated(props, state) {
      
    },
    onBeforeUnmount(props, state) {
      console.log("unmounting - stop polling");
    },
    msg(str) {
      if(Object.keys(this.props.msgs).length == 0) {
          return "*".repeat(str.length);
      }
      if(this.props.msgs[str]) {
        return this.props.msgs[str];
      }
      return "???" + str + "???";
    }
}
</script>

</rssfeed>