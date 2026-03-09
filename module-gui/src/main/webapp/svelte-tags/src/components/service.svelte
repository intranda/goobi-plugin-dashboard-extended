<script>
    import { getContext } from "svelte";

    let { service = {} } = $props();

    const statusColorMap = getContext('statusColor');
    const color = $derived(statusColorMap.get(service.status));
</script>

<dt
    class="service {service.status}"
    style="--service-status-color: {color};">
    <span class="service-badge" title="{service.name}">
        {service.name}
    </span>
</dt>
<dd
    class="service-output"
    style="--service-status-color: {color};">
    {service.output}
</dd>

<style>
    .service {
        align-items: center;
        display: flex;
        font-weight: normal;
        padding-block: 0.35em;
        padding-inline: 0.5em;
    }

    .service,
    .service-output {
        grid-column: 1 / span 2;
        margin-block: 0;
        margin-inline-start: 0.5em;
    }

    .service-output {
        border-inline-start: .25em solid var(--service-status-color);
        padding-block: .5em;
        padding-inline-start: 1em;
    }

    .service-output:not(:last-of-type) {
        border-bottom: 1px solid var(--bs-border-color);
    }

    .service-badge {
        background-color: color-mix(in srgb, var(--service-status-color) 15%, transparent);
        border: 1px solid color-mix(in srgb, var(--service-status-color) 40%, transparent);
        border-radius: 1em;
        color: var(--service-status-color);
        display: inline-block;
        font-size: 0.85em;
        font-weight: 600;
        max-width: 100%;
        overflow: hidden;
        padding: 0.15em 0.65em;
        text-overflow: ellipsis;
        white-space: nowrap;
    }

    @container services (min-width: 400px) {
        .service {
            grid-column: 1;
            overflow: hidden;
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
