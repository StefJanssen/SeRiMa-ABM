These Matlab scripts and functions can be used to import and visualize data generated by AATOM. 

Three scripts are provide:
* importAATOMData
* importAndVisualizeAATOMData
* importBulkAATOMData



importAATOMData: imports data for a single simulation run. The script will request the user to select the folder that contains the log files for the simulation run. This can for instance be the folder '1511173048086_8300_1511173048021'.

importAndVisualizeAATOMData: same script as before, but also visualizes the imported data.

importBulkAATOMData: imports log files for a set of simulation runs. The folder that contains the log files for all the simulation runs that have to be imported needs to be selected by the user. This can for instance be the folder 'logfiles'.

Each of these scripts has a single vector 'ignore' that can be modified. By including a 1, 2 or 3 you ignore the graphData, agentTrace and agentLog files respectively.

As the agentLog file can be of a different structure for each type of simulation, the import function and the visualization function need to be defined by the user. Function headers already exist in the 'customFiles' folder. These should be filled with the right import function. 