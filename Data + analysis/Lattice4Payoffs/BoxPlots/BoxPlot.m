
perturbations = [1,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100];

%Category 1

figure('Name','Category 1','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '1   6-5-1-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')

%Category 2.1

figure('Name','Category 2.1','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '3   10-9-3-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')


%Category 2.2

figure('Name','Category 2.2','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '4   8-7-3-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')

%Category 2.3

figure('Name','Category 2.3','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '6   13-12-8-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')

%Category 3

figure('Name','Category 3','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '8   12-9-1-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')


%Category 4

figure('Name','Category 4','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '9   10-7-1-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')


%Category 5

figure('Name','Category 5','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '10   8-5-1-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')


%Category 6

figure('Name','Category 6','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '13   7-6-4-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')


%Category 7

figure('Name','Category 7','NumberTitle','off','WindowState','maximized');
tiledlayout(2,3)
nexttile
path = '15   10-5-1-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'3by20172.csv'),'NumHeaderLines',2);
nodes = 3*20172;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by20172')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')



%Category 8

figure('Name','Category 8','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '17   13-5-1-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')



%Category 9

figure('Name','Category 9','NumberTitle','off','WindowState','maximized');
tiledlayout(2,2)
nexttile
path = '21   18-5-1-0/';

T = readtable(append(path,'3by5043.csv'),'NumHeaderLines',2);
nodes = 3*5043;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)


title('3by5043')
nexttile

T = readtable(append(path,'3by10092.csv'),'NumHeaderLines',2);
nodes = 3*10092;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('3by10092')
nexttile

T = readtable(append(path,'123by123.csv'),'NumHeaderLines',2);
nodes = 123*123;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('123by123')
nexttile

T = readtable(append(path,'174by174.csv'),'NumHeaderLines',2);
nodes = 174*174;

data = table2array(T(2:end,:));
data = data/nodes*100;

boxchart(data);
ylabel('Cooperation % at the end')
xlabel('Initial Defection %')
xticklabels(perturbations)

title('174by174')






