<script>
    import { getContext } from "svelte";

    let { service = {} } = $props();

    const statusColorMap = getContext('statusColor');
    const color = $derived(statusColorMap.get(service.status));
</script>

<dt
    class="service {service.status}"
    title="{service.status}"
    style="--service-status-color: {color};">
    <span class="font-monospace">{service.status}</span>: {service.name}
</dt>
<dd
    class="service-output"
    style="--service-status-color: {color};">
    {service.output}
</dd>

<style>
    .service {
        font-weight: normal;
    }

    .service,
    .service-output {
        border-inline-start: .25em solid var(--service-status-color);
        grid-column: 1 /span 2;
        height: 100%;
        padding-block: .5em;
        padding-inline-start: 2em;
        margin-inline-start: 0.5em;
    }

    .service-output:not(:last-of-type) {
        border-bottom: 1px solid var(--bs-border-color);
    }

    @container services (min-width: 400px) {
        .service {
            grid-column: 1;
        }
        .service:not(:last-of-type) {
            border-bottom: 1px solid var(--bs-border-color);
        }
        .service-output {
            border: 0;
            grid-column: 2;
            padding-inline-start: 0;
            margin-inline-start: 0;
        }
    }
</style>