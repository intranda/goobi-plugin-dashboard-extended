<script>
	import { onMount, setContext } from 'svelte';
	import Service from './components/service.svelte';
	import Indicator from './components/indicator.svelte';

	let { msgs = {} } = $props();

	let hosts = $state([]);
	let show = $state(false);

	const statusColor = new Map([
		['OK', 'green'],
		['WARNING', 'orange'],
		['CRITICAL', 'red'],
		['UNKNOWN', 'gray'],
	]);
	const failStates = ['CRITICAL', 'WARNING', 'UNKNOWN'];

	setContext('statusColor', statusColor);

	onMount(() => reload());

	const reload = async () => {
		const path = window.location.pathname.replace(/\/uii\/.*/, '');
		const resp = await fetch(`${path}/api/plugins/exdashboard/icinga2`);
		const json = await resp.json();

		hosts = json.map(host => {
			const grouped = Object.groupBy(host.services ?? [], s => s.status);

			const servicesByStatus = {
				OK: [],
				WARNING: [],
				CRITICAL: [],
				UNKNOWN: [],
				...grouped
			};

			const summary = host.status === 'DOWN'
				? 'CRITICAL'
				: failStates.find(s => servicesByStatus[s]?.length > 0) ?? 'OK';

			return {
				...host,
				servicesByStatus,
				summary,
			};
		});
	};

	const msg = (str) => {
		if (Object.keys(msgs).length === 0) return '*'.repeat(str.length);
		return msgs[str] ?? `???${str}???`;
	};

	const toggleShow = () => show = !show;
</script>


<div class="box box--neutral box--padded">
	<div class="box__content">
		<div class="box__content-inner">

			<div class="box__title">
				<h2>
					<span class="fa fa-binoculars" aria-hidden="true"></span>
					Monitoring
				</h2>

				<div class="actions d-flex">

					<!-- reload all data again -->
					<button
						type="button"
						class="btn d-flex align-items-center btn--title-action"
						onclick={reload}
						title="{msg('reload')}">
						<span class="fa fa-refresh" aria-hidden="true"></span>
					</button>
					<!-- // reload all data again -->

					<!-- switch view -->
					<button
						type="button"
						class="btn d-flex align-items-center btn--title-action"
						onclick={toggleShow}
						title="{show ? msg('showBoxDetailsOff') : msg('showBoxDetailsOn')}">
						<span class="fa {show ? 'fa-angle-up' : 'fa-angle-down'}" aria-hidden="true"></span>
					</button>
					<!-- // switch view -->

				</div>
			</div>

			<div class="p-3">
				{#if hosts.length == 0}
					<div class="p-3">
						<div class="alert alert-info alert-dismissable">
							{msg('dashboard_Icinga2NotLoaded')}
						</div>
					</div>
				{/if}
				{#each hosts as host, i}
					<div class="{i > 0 ? 'mt-3' : ''}">
						<div
							class="host">
							<Indicator status={host.summary} />
							<span class="host-name">{host.name}: {host.summary}</span>
							<span class="filler"></span>
						</div>

						<dl class="definition-list">
							{#each host.servicesByStatus['UNKNOWN'] ?? [] as service}
								<Service {service} />
							{/each}
							{#each host.servicesByStatus['CRITICAL'] ?? [] as service}
								<Service {service} />
							{/each}
							{#each host.servicesByStatus['WARNING'] ?? [] as service}
								<Service {service} />
							{/each}
							{#if show}
								{#each host.servicesByStatus['OK'] ?? [] as service}
									<Service {service} />
								{/each}
							{/if}
						</dl>
					</div>
				{/each}
			</div>
		</div>
	</div>
</div>

<style>
	.host {
		align-items: center;
		display: flex;
		gap: 1rem;
		justify-content: start;
		margin-block-end: 1rem;
		width: 100%;
	}

	.host-name {
		flex: 0 1 auto;
	}

	.filler {
		background: linear-gradient(var(--bs-border-color), var(--bs-border-color)) no-repeat center/100% 2px;
		flex: 1 0 auto;
		height: 2px;
		padding-inline-start: 1em;
	}
</style>
