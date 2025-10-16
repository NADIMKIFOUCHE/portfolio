(*question 1*)
type etat=int ;;
type transition={source: etat ; caractere: char ; cible: etat} ;;
type aef=
  {alphabet:char list; etat_initial:int; q:etat list; f:etat list ;transition:transition list };; 

let a={alphabet=['a';'b']; etat_initial=1; q=[1;2;3]; f=[3];
       transition=[{source=1; caractere='a'; cible=2};
                   {source=2;caractere='a'; cible=2};{source=2;caractere='b'; cible=3}]};;
  
let b={alphabet=['a';'b']; etat_initial=1; q=[1;2;3]; f=[1;3];
       transition=[{source=1; caractere='a'; cible=2};{source=2;caractere='a'; cible=2};{source=4;caractere='b'; cible=4}]};;

let c= {alphabet=['a';'b']; etat_initial=2; q=[1;2;3]; f=[1;2];
        transition=[{source=1; caractere='a'; cible=2};{source=2;caractere='a'; cible=2};{source=2;caractere='b'; cible=3}]};;

(*question 2*)
let rec est_dans_la_liste: int->etat list ->bool= fun x lst -> match lst with
  |[]->false
  |[a]-> a=x 
  |h::t->if h=x then true else est_dans_la_liste x t ;;

let rec est_inclus: etat list-> etat list -> bool = fun lst1 lst2 -> match lst1 with
  |[]->true
  |h::t-> if est_dans_la_liste h lst2 then est_inclus t lst2 else false ;;

let est_source: transition->etat ->bool=fun transition x -> transition.source=x;;
  
let est_cible: transition->etat ->bool=fun transition x -> transition.cible=x ;;

let source_plus_cible : aef->bool = fun aef->
  let q = aef.q in
  let transitions = aef.transition in
  List.for_all (fun x -> 
      List.exists (fun t -> est_source t x || est_cible t x) transitions ) q ;; 

let est_correct: aef->bool = fun aef ->
  (List.length aef.q)>0 && est_inclus aef.f aef.q
  && source_plus_cible aef ;;

(*question 3*)
let est_complet: aef ->bool= fun aef-> 
  let q = aef.q in
  let car= aef.alphabet in
  List.for_all (fun t ->
      List.for_all (fun c ->
          List.exists(fun x-> x.source=t && x.caractere=c) aef.transition) car )q;; 


(*question 4*)
let caractere_manquant: aef ->(etat*char) list= fun aef-> 
  let q = aef.q in
  let car= aef.alphabet in 
  List.fold_left (fun acc t ->
      List.fold_left (fun acc2 c ->
          if( List.exists(fun x-> x.source=t && x.caractere=c) aef.transition)=false then
            (t,c)::acc2 else acc2) acc car )[] q;; 


let rec transition_manquante =fun lst-> match lst with 
  |[]->[]
  |(x,y)::t->{source=x; caractere=y ; cible= -1}:: transition_manquante t ;;

let completer: aef-> aef= fun aef ->
  let acc= transition_manquante (caractere_manquant aef) in 
  { alphabet = aef.alphabet; 
    etat_initial= aef.etat_initial; 
    q= aef.q@ [-1];
    f = aef.f;
    transition =aef.transition@acc} ;;


(*question 5*)
let langage_vide :aef -> bool= fun aef -> 
  (List.length aef.f)=0 || (List.length aef.transition)=0 ;;

(*question 6*) 
let rec sans_doublons = fun liste ->
  match liste with
  | [] -> true
  | h::t -> not(List.mem h t) &&  sans_doublons t
;;

let est_deterministe : aef -> bool = fun aef ->
  let rec aux = fun lst ->
    match lst with
    | [] -> true
    | [x] -> true
    | h::t -> if not (
        sans_doublons (
          List.map (fun x -> x.caractere) 
            (List.filter (fun x -> x.source = h) aef.transition))) then false else aux t
  in aux aef.q;;


(* question 7 *)
let lecture_car : aef -> etat ->char-> etat option = 
  fun aef etat c->
    match List.find_opt (fun t -> t.source = etat && t.caractere = c) aef.transition with
    | None -> None
    | Some t -> Some t.cible
;; 

(* question 8 *)

let lecture_mot : aef -> etat -> string -> etat = 
  fun aef etat_initial mot ->
    let n = String.length mot in
    let rec aux i etat_courant =
      if i = n then etat_courant
      else
        match lecture_car aef etat_courant (mot.[i]) with
        | None -> etat_courant
        | Some etat_suivant -> aux (i+1) etat_suivant
    in aux 0 etat_initial 
;;

(* question 9 *)

(* Définir une fonction accepter_mot qui prend en arguments un mot et un AEF a et qui permet
    de lire le mot à partir de l’état initial de a. *)

let accepter_mot : aef -> string -> bool = 
  fun aef mot -> 
    let x = lecture_mot aef aef.etat_initial mot in 
    if List.exists (fun t-> t=x) aef.f then true else false;;


(* question 10 *)

let rec verif_initialiser_q0:aef->aef->etat->etat= fun a1 a2 x -> 
  if (List.exists(fun t -> t!=x) a1.q && List.exists(fun t -> t!=x)a2.q) then x
  else verif_initialiser_q0 a1 a2 (x-1) ;;

let f1_union_f2: aef->aef->etat->etat list= fun a1 a2 q0-> 
  let q1=a1.etat_initial in 
  let q2=a2.etat_initial in 
  if List.exists(fun t->t=q1)a1.f || List.exists(fun t->t=q2)a2.f then 
    a1.f @ a2.f @ (q0::[]) 
  else 
    a1.f @ a2.f;;



let rec rajouter_transition_char:aef->etat list->char->transition list = 
  fun a lstq c-> match lstq with 
    |[]->[]
    |h::t->{source=a.etat_initial; caractere=c; cible= h}::rajouter_transition_char a t c 
;;

let rec rajouter_transition:aef->char list->transition list= fun a j -> match j with 
  |[]->[]
  |h::t->rajouter_transition_char a a.q h @ rajouter_transition a t 
;;

let union: aef->aef->aef= fun a1 a2 -> 
  let q0=verif_initialiser_q0 a1 a2 100 in 
  { alphabet = a1.alphabet; 
    etat_initial= q0 ; 
    q= a1.q@ a2.q @ (q0::[]);
    f = f1_union_f2 a1 a2 q0;
    transition =a1.transition @ a2.transition
                @ rajouter_transition a1 a1.alphabet @  rajouter_transition a2 a2.alphabet };;

union a b 

(* question 11 *)

let etat_final: aef->aef->etat list = fun a1 a2->
  if List.exists(fun x->x=a2.etat_initial) a2.f then a1.f@a2.f else a2.f;; 

let rec parcourirf1:etat list->char->etat->transition list = 
  fun lstf c q-> match lstf with 
    |[]->[]
    |h::t->{source=h; caractere=c; cible= q}::parcourirf1 t c q 
;;

let rec parcourir_alphabet: etat list->char list->etat->transition list = 
  fun f lstc  q -> match lstc with 
    |[]->[]
    |h::t->parcourirf1 f h q @ parcourir_alphabet f t q 
;;

let rec parcourirq2: etat list->char list-> etat list ->transition list= 
  fun f c lstq -> match lstq with 
    |[]->[]
    |h::t->parcourir_alphabet f c h @ parcourirq2 f c t
;;

let union2: aef->aef->aef= fun a1 a2 -> 
  { alphabet = a1.alphabet; 
    etat_initial= List.hd a1.q ; 
    q= a1.q@ a2.q ;
    f = etat_final a1 a2;
    transition =a1.transition @ a2.transition @ parcourirq2 a1.f a1.alphabet a2.q } ;;

(* question 12 *) 

let afficher= fun q -> 
  let transitions = q.transition in
  List.iter (fun t ->
      Printf.printf "%d --> (%c) %d\n" t.source t.caractere t.cible ) transitions
;;






