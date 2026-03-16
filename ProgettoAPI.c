#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#include <string.h>
#include <math.h>


#define MAX_VICINI 6
#define MAX_AEREE 5
#define OFFSET_MAX 6

typedef struct {
    int index;                                                                                     //indice esagono
    int cost;                                                                                      //costo
}dueint;

typedef struct n_t2 {
    int x;
    int y;
    int cost;
    int n_next;
    int vis;
    int dis;
    int n_fly;
    int flycostsum;
    int next[MAX_VICINI];                                                                           //al più 6 vicini, se l'esagono è ai bordi, i puntatori vanno a NULL
    dueint fly[MAX_AEREE];                                                                          //al più 5 rotte aeree
}hex;

typedef struct n_t1{
    hex data;
    struct n_t1 *next;
}nodo_t;
typedef nodo_t* list;
typedef struct {
    int x;
    int y;
    int z;
} cubecoord;

static inline int getidxcoord(int x,int y,int cols){
    return y*cols + x;
    
}
static inline void getnext(hex*restrict map,int rows,int cols){
    int dim = rows*cols;
    //offset vicini
    int offsetpari[OFFSET_MAX];
    int offsetdispari[OFFSET_MAX];
    
    offsetpari[0] = +1;
    offsetpari[1] = cols;
    offsetpari[2] = cols-1;
    offsetpari[3] = -1;
    offsetpari[4] = -cols-1;
    offsetpari[5] = -cols;
    offsetdispari[0] = +1;
    offsetdispari[1] = cols+1;
    offsetdispari[2] = cols;
    offsetdispari[3] = -1;
    offsetdispari[4] = -cols+1;
    offsetdispari[5] = -cols;                                            
    for(int i = 0;i< dim; i++){
        int y = i/cols;
        int x = i%cols;
        int *offset;                                            //offset da usare
        if(y%2 == 0){
            offset = offsetpari;
        }else{
            offset = offsetdispari;
        }
        int k=0;
        for(int j = 0; j<OFFSET_MAX;j++){
            int nex = i + offset[j];
            int dup = 0;
            if(nex<0||nex>=dim){
                continue;
            }
            int nexx = nex%cols;
            int nexy = nex/cols;
            if(abs(nexx - x)>1||abs(nexy - y)>1){
                continue;
            }
            if(cols == 2 && y%2 != 0 && offset[j] == 1) continue;

            for(int g = 0;g<k;g++){
                if(map[i].next[g] == nex){
                    dup = 1;
                    break;
                }
            }
            if(dup == 1||nex == i) continue;
            map[i].next[k] = nex;
            k++;

        }
        map[i].n_next = k;
    }
}
static inline void onecost(hex*map,int rows,int cols){
    for(int i=0; i<rows*cols;i++){
        map[i].cost=1;
    }
}
static inline cubecoord offset(int col,int row){
    cubecoord cube;
    cube.x = col-(row - (row & 1))/2;
    cube.z = row;
    cube.y = -cube.x-cube.z;
    return cube;
}
static inline int distcub(cubecoord a, cubecoord b){
    return (abs(a.x - b.x) + abs(a.y - b.y) + abs(a.z - b.z))/2;
    
}
static inline int distanza(int col1,int row1,int col2, int row2){
    cubecoord a = offset(col1,row1);
    cubecoord b = offset(col2,row2);
    int d = distcub(a,b);
    return d;
}
static inline int arrayraggio(int*array, int raggio,int rows,int cols,int idx,int *dist){                        //non faccio distesagoni per ogni esagono della mappa ma mi fermo se raggiungo una distanza maggiore
    int count = 0;
    int dim = rows*cols;
    int col1 = idx%cols;
    int row1 = idx/cols;
    for(int i =0; i<dim;i++){
        int col2 = i%cols;
        int row2 = i/cols;
        int d = distanza(col1,row1,col2,row2);
        if(d<raggio){
            array[count] = i;
            dist[count] = d;
            count++;
        }
    }                                                                           
    return count;                                                                                  //modifica l'array e restituisce count che è il numero di esagoni nel raggio che stanno nell'array
}
static inline void change_cost(int x, int y, int v,float raggio,int cols,hex*restrict map,int rows){
    int idx;
    int dim = rows*cols;
    int array_raggio[dim];
    int count = 0;                                                                                  //dimensione 'utile' array_raggio
    idx = y*cols + x;
    int dist[dim];
    
    count = arrayraggio(array_raggio,raggio,rows,cols,idx,dist);

    for(int i=0;i<count;i++){
        int sum = 0;
        int d = dist[i];
        float fattore = (float)(raggio -d)/(float)raggio;
        int k = (int) floorf (v * fattore);
        map[array_raggio[i]].cost=map[array_raggio[i]].cost + k;
        if(map[array_raggio[i]].cost<=0){
            map[array_raggio[i]].cost = 0;
            for(int j = 0;j<map[array_raggio[i]].n_fly;j++){
                map[array_raggio[i]].fly[j].cost = map[array_raggio[i]].fly[j].cost + k;
                sum += map[array_raggio[i]].fly[j].cost;
                if(map[array_raggio[i]].fly[j].cost<=0){
                    map[array_raggio[i]].fly[j].cost = 0;
                }
            }
        }   
    
    map[array_raggio[i]].flycostsum = sum;
    }  
}
static inline int costo_air_route(int idx,hex*map){
    return (map[idx].cost + map[idx].flycostsum)/(map[idx].n_fly + 1);
}
static inline int toggle_air_route(int x1,int y1, int x2,int y2,int cols,hex*map){                                
    int i;
    int j;                                          
    i = y1*cols +x1;
    j = y2*cols + x2;
    for(int k=0;k<map[i].n_fly; k++){
        if(map[i].fly[k].index == j){
            map[i].flycostsum -= map[i].fly[k].cost;
            map[i].fly[k].index = map[i].fly[map[i].n_fly - 1].index;
            map[i].fly[k].cost = map[i].fly[map[i].n_fly - 1].cost;                                 //SCAMBIO L'ULTIMO ESAGONO CON QUELLO CHE ELIMINO, E DIMINUISCO IL NUMERO DI "VOLI"
            
            map[i].n_fly--;                                                  
            return 2;                                                                               //RITORNO 2 SE LA ROTTA AEREA È STATA TOLTA  
        }
    }
    if(map[i].n_fly == 5){
        return 5;                                                                                   //RITORNA 5 SE È STATO RAGGIUNTO IL NUMERO MASSIMO DI ROTTE AEREE
    }
    map[i].fly[map[i].n_fly].index = j;
    map[i].fly[map[i].n_fly].cost = costo_air_route(i,map);
    map[i].flycostsum +=map[i].fly[map[i].n_fly].cost;
    map[i].n_fly++;
    return 1;                                                                                       //RITORNO 1 SE LA ROTTA AEREA È STATA AGGIUNTA
}

static inline void swap_dueint(dueint *i, dueint *j){
    dueint tmp;
    tmp = *i;
    *i=*j;
    *j=tmp;
}
static inline int heap_push(dueint *restrict heap, int *restrict heap_size,dueint new,int cost,hex*restrict map,int *restrict pos,int *restrict visitati,int visit){
    int idx = new.index;
    int i;
    if(pos[idx]!=-1){
        if(map[idx].dis > cost){
            new.cost = cost;
            map[idx].dis = cost;
            heap[pos[idx]] = new;

            //risalgo nell'heap
            i = pos[idx];
            while(i>0){
            int p = (i-1)/2;
            if(heap[p].cost <= heap[i].cost ){
                break;
            }
            swap_dueint(&heap[p],&heap[i]);
            pos[heap[p].index] = p;
            pos[heap[i].index] = i;
            i = p;
            }
            visitati[visit] = idx;
            visit++;
        }

        return visit;   
    }
    new.cost = cost;
    map[idx].dis = cost;
    i = (*heap_size)++;
    heap[i] = new;
    pos[idx] = i;
    while(i>0){
        int p = (i-1)/2;
        if(heap[p].cost <= heap[i].cost ){
            break;
        }
        swap_dueint(&heap[p],&heap[i]);
        pos[heap[p].index] = p;
        pos[heap[i].index] = i;
        i = p;
    }
    visitati[visit] = idx;
    visit++;
    return visit;
}   

static inline void fix_heap(dueint *restrict heap,int *restrict heap_size, int *restrict pos){
    int i = 0;
    while(1){
        int sin = 1 + 2*i;
        int des = 2 + 2*i;
        int minore = i;
        if(sin<*heap_size && heap[sin].cost <heap[minore].cost){
            minore = sin;
        }
        if(des<*heap_size && heap[des].cost <heap[minore].cost){
            minore = des;
        }
        if(minore == i){
            break;
        }
        swap_dueint(&heap[i],&heap[minore]);
        pos[heap[i].index] = i;
        pos[heap[minore].index] = minore;
        i = minore;
    }
}
static inline dueint minheap_pop(dueint *restrict heap,int *restrict heap_size, int *restrict pos){
    int last = *heap_size - 1;
    dueint ret = heap[0];
    swap_dueint(&heap[0],&heap[last]);
    pos[ret.index] = -1;
    if(last!=0){
        pos[heap[0].index] = 0;
    }
    (*heap_size)--;
    fix_heap(heap,heap_size,pos);
    return ret;
}
static inline int travel_cost(hex *restrict map,int rows,int cols,int x1,int y1,int x2,int y2){
    dueint heap[rows*cols];
    dueint start;
    dueint end;
    dueint pop;
    dueint first;
    int dim = rows*cols;
    int pos[dim];
    int visitati[dim];
    
    int visit = 0;
    for(int i = 0;i<dim;i++){
        pos[i] = -1;
    }
    
    int heap_size = 0;
    
    start.index = getidxcoord(x1,y1,cols);
    start.cost = map[start.index].cost;
    end.index = getidxcoord(x2,y2,cols);
    map[start.index].dis = 0;
    first.index = start.index;
    first.cost = 0;
    heap[0] = first;
    pos[first.index] = 0;
    heap_size = 1;
    visitati[visit] = first.index;
    visit++;

    while(heap_size>0){
        pop =minheap_pop(heap,&heap_size,pos);
        hex*corrente = &map[pop.index];
        if(corrente ->vis == 1) continue;
        corrente ->vis = 1;
        visitati[visit] = pop.index;
        if(pop.index == end.index){
            break;
        }
        if(corrente ->cost == 0) continue;
        //esploro i vicini
        for(int i = 0;i<corrente ->n_next;i++){
            int indice = corrente->next[i];
            hex*vicino=&map[indice];
            if(vicino->vis == 1) continue;
            int nuovocosto = corrente ->dis + corrente ->cost;
            dueint nuovo;
            nuovo.index = indice;
            nuovo.cost = nuovocosto;
            if(pos[indice]!=-1 && vicino->dis<= nuovocosto) continue;
            visit = heap_push(heap,&heap_size,nuovo,nuovocosto,map,pos,visitati,visit);
        }
        for(int i = 0;i<map[pop.index].n_fly;i++){
            int indice = map[pop.index].fly[i].index;
            hex*aereo = &map[indice];
            if(aereo->vis  == 1 || corrente->fly[i].cost == 0){
                continue;
            }
            int nuovocosto = corrente ->dis + corrente ->fly[i].cost;
            dueint nuovo;
            nuovo.index = indice;
            nuovo.cost = nuovocosto;
            if(pos[indice]!=-1 && aereo->dis<= nuovocosto) continue;
            visit = heap_push(heap,&heap_size,nuovo,nuovocosto,map,pos,visitati,visit);
        }
    }
    if(pop.index !=end.index){
        for(int i = 0;i<visit;i++){
            int dito = visitati[i];
            map[dito].vis = 0;
            map[dito].dis = INT_MAX;  
        }
        return INT_MAX;
    }
    int risultato = map[end.index].dis;
    
    for(int i = 0;i<visit;i++){
        int dito = visitati[i];
        map[dito].vis = 0;
        map[dito].dis = INT_MAX;    
    }
    return risultato;
} 

//SEMBRA FUNZIONARE

hex *init(int rows,int cols){                                                                       
    if(cols<1 || rows<1){                                                                           
        return NULL;
    }
    int dim = 0;
    dim = rows*cols;
    hex *map = malloc(dim*sizeof(hex));                                                             
    for(int i=0;i<dim;i++){
        map[i].cost = 1;
        map[i].n_fly=0;
        map[i].flycostsum = 0;
        map[i].dis = INT_MAX;
        map[i].n_next = 0;
        map[i].vis = 0;  
    }
    getnext(map,rows,cols);                                 

    return map;
}
static inline void destroy_map(hex*map){
    free(map);
}  

int main(void){
    int es = 0;
    int rows = -1;
    int cols = -1;
    int x;
    int y;
    int v;
    int x1;
    int y1;
    hex *map = NULL;
    char riga[256];
    char *token[5];
    while(fgets(riga,sizeof(riga),stdin)){
        token[0] = strtok(riga," ");
        for(int t = 1;t<5;t++){
            token[t] = strtok(NULL," ");
        }
        if(!strcmp(token[0],"travel_cost")){
            x = atoi(token[1]);
            y = atoi(token[2]);;
            if(x>=cols||y>=rows || x<0||y<0){
                fprintf(stdout,"-1\n");
                continue;
            }
            x1 = atoi(token[3]);
            y1 = atoi(token[4]);
            if(x1>=cols||y1>=rows || x1<0||y1<0){
                fprintf(stdout,"-1\n");
                continue;
            }
            v = travel_cost(map,rows,cols,x,y,x1,y1);
            if(v == INT_MAX){
                fprintf(stdout,"-1\n");
                continue;
            }
            fprintf(stdout,"%d\n",v);
        }else if(!strcmp(token[0],"init")){
            if(es){
                destroy_map(map);
            }
            rows = atoi(token[2]);
            cols = atoi(token[1]);
            map = init(rows,cols);
            es = 1;
            fprintf(stdout,"OK\n");
        }else if(!strcmp(token[0],"change_cost")){
            x = atoi(token[1]);
            y = atoi(token[2]);
            if(x>=cols||y>=rows || x<0||y<0){
                fprintf(stdout,"KO\n");
                continue;
            }
            float raggio = atof(token[4]); 
            if (raggio == 0){
                fprintf(stdout,"KO\n");
                continue;
            }
            v = atoi(token[3]);
            if(v<-10 || v>10){
                fprintf(stdout,"KO\n");
                continue;
            }
            change_cost(x,y,v,raggio,cols,map,rows);
            fprintf(stdout,"OK\n");
            
        }else if(!strcmp(token[0],"toggle_air_route")){
            x = atoi(token[1]);
            y = atoi(token[2]);
            if(x>=cols||y>=rows || x<0||y<0){
                fprintf(stdout,"KO\n");
                continue;
            }
            x1 = atoi(token[3]);
            y1 = atoi(token[4]);
            if(x1>=cols||y1>=rows || x1<0||y1<0){
                fprintf(stdout,"KO\n");
                continue;
            }
            v = toggle_air_route(x,y,x1,y1,cols,map);
            if(v == 5){
                fprintf(stdout,"KO\n");
                continue;
            }
            fprintf(stdout,"OK\n");
        }
    }
    if(es){
        destroy_map(map);
    }
    return 0;   
}