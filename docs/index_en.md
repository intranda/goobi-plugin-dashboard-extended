---
title: Extended Dashboard
identifier: intranda_dashboard_extended
description: Dashboard plugin for extended information display
published: true
---

## Introduction
This dashboard plugin provides an improved overview through detailed display options. For example, the most recently edited tasks or relevant statistics can be shown.

## Installation
In order to use the plugin, the following files must be installed:

```bash
/opt/digiverso/goobi/plugins/dashboard/plugin-dashboard-extended-base.jar
/opt/digiverso/goobi/plugins/GUI/plugin-dashboard-extended-gui.jar
/opt/digiverso/goobi/config/plugin_intranda_dashboard_extended.xml
```

To use this plugin, the user must select the value `intranda_dashboard_extended` within the dashboard settings. 

![Selection of the dashboard in the user settings](screen1_en.png)


## Overview and functionality
If the plugin is installed correctly and users have set it as their dashboard, it will be visible after logging into Goobi workflow instead of the start page.

![User interface of the dashboard](screen2_en.png)

## Configuration
The plugin is configured in the file `plugin_intranda_dashboard_extended.xml` as shown here:

{{CONFIG_CONTENT}}

The following table contains a summary of the parameters and their descriptions:

Parameter               | Explanation
------------------------|------------------------------------
`<itm-show>`            | This parameter defines whether the currently running jobs of the intranda Task Manager should be displayed. 
`<itm-cache-time>`      | This value is specified in milliseconds and defines how often the values from the intranda Task Manager should be updated.
`<itm-url>`             | The URL at which the intranda Task Manager can be accessed is specified here.
`<rss-show>`            | This parameter defines whether news that can be retrieved via RSS feed should be displayed.
`<rss-cache-time>`      | This value is specified in milliseconds and indicates how often the RSS feed should be updated.
`<rss-url>`             | This parameter specifies the website from which the RSS feed is to be loaded.
`<rss-title>`           | The title that is to appear above the news items is defined here.
`<search-show>`         | This parameter determines whether the ‘Search’ form should be displayed.
`<tasks-show>`          | This parameter defines whether the ‘Recently completed tasks’ area should be displayed.
`<tasks-show-size>`     | Here you can specify how many of the recently completed tasks should be displayed.
`<tasks-history>`       | This can be used to display the history of the last tasks. 
`<tasks-history-title>` | This parameter can be used to specify which task type is to be displayed.
`<tasks-history-period>` | This parameter defines the maximum length of time (in days) that may have passed since the last edit for it to still be displayed.
`<tasks-latestChanges>` | Here you can specify whether the most recently processed tasks should be displayed.
`<tasks-latestChanges-size>` | This parameter specifies the number of the last changes to be shown.
`<statistics-show>`     | Here you define whether statistics are to be displayed.
`<batches-show>`        | This parameter specifies whether the batches are to be displayed.
`<batches-timerange-start>` | Here you specify how many months ago the batches were started to be processed so that they are displayed.
`<batches-timerange-end>` | Here you can specify how many months after the start of processing the batches are displayed.
`<processTemplates-show>` | This parameter defines whether the production templates are to be displayed.
`<processTemplates-show-statusColumn>` | Here you can specify whether the status column should be displayed.
`<processTemplates-show-projectColumn>` | Here you can specify whether the project column should be displayed.
`<processTemplates-show-massImportButton>` | Here you can specify whether the bulk import button should be displayed.
`<queue-show>`.           | This parameter defines whether the dashboard should display how many processes are currently in the queue and what their status is.