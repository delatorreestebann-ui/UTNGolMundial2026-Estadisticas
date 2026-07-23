--
-- PostgreSQL database dump
--

\restrict n0JB798g8yLznWl0VlvjdvBH6nUHlg1DjEHyY0gd53wvBNw5T0s9z8478GqAyL3

-- Dumped from database version 18.3
-- Dumped by pg_dump version 18.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: auditoria; Type: TABLE; Schema: public; Owner: estadisticas_user
--

CREATE TABLE public.auditoria (
    idauditoria integer NOT NULL,
    tipoaccion character varying(50) NOT NULL,
    fechahora timestamp without time zone DEFAULT now() NOT NULL,
    tablaafectada character varying(50),
    descripcion character varying(500),
    idusuario integer
);


ALTER TABLE public.auditoria OWNER TO estadisticas_user;

--
-- Name: auditoria_idauditoria_seq; Type: SEQUENCE; Schema: public; Owner: estadisticas_user
--

CREATE SEQUENCE public.auditoria_idauditoria_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.auditoria_idauditoria_seq OWNER TO estadisticas_user;

--
-- Name: auditoria_idauditoria_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: estadisticas_user
--

ALTER SEQUENCE public.auditoria_idauditoria_seq OWNED BY public.auditoria.idauditoria;


--
-- Name: ciudad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ciudad (
    idciudad integer NOT NULL,
    nombre character varying(100),
    idpais integer
);


ALTER TABLE public.ciudad OWNER TO postgres;

--
-- Name: ciudad_idciudad_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ciudad_idciudad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ciudad_idciudad_seq OWNER TO postgres;

--
-- Name: ciudad_idciudad_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ciudad_idciudad_seq OWNED BY public.ciudad.idciudad;


--
-- Name: confederacion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.confederacion (
    idconfederacion integer NOT NULL,
    nombre character varying(100)
);


ALTER TABLE public.confederacion OWNER TO postgres;

--
-- Name: confederacion_idconfederacion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.confederacion_idconfederacion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.confederacion_idconfederacion_seq OWNER TO postgres;

--
-- Name: confederacion_idconfederacion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.confederacion_idconfederacion_seq OWNED BY public.confederacion.idconfederacion;


--
-- Name: fase; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.fase (
    idfase integer NOT NULL,
    codigo character varying(50),
    nombre character varying(100),
    fechainicio date,
    fechafin date
);


ALTER TABLE public.fase OWNER TO postgres;

--
-- Name: fase_idfase_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.fase_idfase_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.fase_idfase_seq OWNER TO postgres;

--
-- Name: fase_idfase_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.fase_idfase_seq OWNED BY public.fase.idfase;


--
-- Name: grupo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.grupo (
    idgrupo integer NOT NULL,
    codigo character varying(50),
    nombre character varying(100)
);


ALTER TABLE public.grupo OWNER TO postgres;

--
-- Name: grupo_idgrupo_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.grupo_idgrupo_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.grupo_idgrupo_seq OWNER TO postgres;

--
-- Name: grupo_idgrupo_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.grupo_idgrupo_seq OWNED BY public.grupo.idgrupo;


--
-- Name: pais; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pais (
    idpais integer NOT NULL,
    nombre character varying(100)
);


ALTER TABLE public.pais OWNER TO postgres;

--
-- Name: pais_idpais_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pais_idpais_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pais_idpais_seq OWNER TO postgres;

--
-- Name: pais_idpais_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pais_idpais_seq OWNED BY public.pais.idpais;


--
-- Name: partido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.partido (
    idpartido integer NOT NULL,
    numeropartidofifa integer,
    fechahorautc timestamp without time zone,
    estado character varying(50),
    goleslocal integer,
    golesvisitante integer,
    cuotaempate numeric,
    cuotavisitante numeric,
    cuotalocal numeric,
    fecharesultadoregistrado timestamp without time zone,
    idfase integer,
    idsede integer,
    idgrupo integer,
    idseleccionlocal integer,
    idseleccionvisitante integer
);


ALTER TABLE public.partido OWNER TO postgres;

--
-- Name: partido_idpartido_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.partido_idpartido_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.partido_idpartido_seq OWNER TO postgres;

--
-- Name: partido_idpartido_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.partido_idpartido_seq OWNED BY public.partido.idpartido;


--
-- Name: rol; Type: TABLE; Schema: public; Owner: estadisticas_user
--

CREATE TABLE public.rol (
    idrol integer NOT NULL,
    nombre character varying(50) NOT NULL,
    descripcion character varying(200)
);


ALTER TABLE public.rol OWNER TO estadisticas_user;

--
-- Name: rol_idrol_seq; Type: SEQUENCE; Schema: public; Owner: estadisticas_user
--

CREATE SEQUENCE public.rol_idrol_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.rol_idrol_seq OWNER TO estadisticas_user;

--
-- Name: rol_idrol_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: estadisticas_user
--

ALTER SEQUENCE public.rol_idrol_seq OWNED BY public.rol.idrol;


--
-- Name: sede; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sede (
    idsede integer NOT NULL,
    estadio character varying(100),
    capacidad integer,
    idciudad integer
);


ALTER TABLE public.sede OWNER TO postgres;

--
-- Name: sede_idsede_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sede_idsede_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sede_idsede_seq OWNER TO postgres;

--
-- Name: sede_idsede_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sede_idsede_seq OWNED BY public.sede.idsede;


--
-- Name: seleccion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seleccion (
    idseleccion integer NOT NULL,
    nombre character varying(100),
    codigofifa character varying(10),
    esanfitrion boolean,
    clasificacion character varying(100),
    partidosjugados integer DEFAULT 0,
    puntos integer DEFAULT 0,
    partidosganados integer DEFAULT 0,
    partidosempatados integer DEFAULT 0,
    partidosperdidos integer DEFAULT 0,
    golesfavor integer DEFAULT 0,
    golescontra integer DEFAULT 0,
    idgrupo integer,
    idconfederacion integer
);


ALTER TABLE public.seleccion OWNER TO postgres;

--
-- Name: seleccion_idseleccion_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seleccion_idseleccion_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seleccion_idseleccion_seq OWNER TO postgres;

--
-- Name: seleccion_idseleccion_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.seleccion_idseleccion_seq OWNED BY public.seleccion.idseleccion;


--
-- Name: usuario; Type: TABLE; Schema: public; Owner: estadisticas_user
--

CREATE TABLE public.usuario (
    idusuario integer NOT NULL,
    nombre character varying(100) NOT NULL,
    email character varying(150),
    username character varying(50) NOT NULL,
    passwordhash character varying(255) NOT NULL,
    estado boolean DEFAULT true NOT NULL,
    fecharegistro timestamp without time zone DEFAULT now() NOT NULL,
    ultimoacceso timestamp without time zone,
    idrol integer NOT NULL
);


ALTER TABLE public.usuario OWNER TO estadisticas_user;

--
-- Name: usuario_idusuario_seq; Type: SEQUENCE; Schema: public; Owner: estadisticas_user
--

CREATE SEQUENCE public.usuario_idusuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_idusuario_seq OWNER TO estadisticas_user;

--
-- Name: usuario_idusuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: estadisticas_user
--

ALTER SEQUENCE public.usuario_idusuario_seq OWNED BY public.usuario.idusuario;


--
-- Name: auditoria idauditoria; Type: DEFAULT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.auditoria ALTER COLUMN idauditoria SET DEFAULT nextval('public.auditoria_idauditoria_seq'::regclass);


--
-- Name: ciudad idciudad; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ciudad ALTER COLUMN idciudad SET DEFAULT nextval('public.ciudad_idciudad_seq'::regclass);


--
-- Name: confederacion idconfederacion; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confederacion ALTER COLUMN idconfederacion SET DEFAULT nextval('public.confederacion_idconfederacion_seq'::regclass);


--
-- Name: fase idfase; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fase ALTER COLUMN idfase SET DEFAULT nextval('public.fase_idfase_seq'::regclass);


--
-- Name: grupo idgrupo; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grupo ALTER COLUMN idgrupo SET DEFAULT nextval('public.grupo_idgrupo_seq'::regclass);


--
-- Name: pais idpais; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais ALTER COLUMN idpais SET DEFAULT nextval('public.pais_idpais_seq'::regclass);


--
-- Name: partido idpartido; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido ALTER COLUMN idpartido SET DEFAULT nextval('public.partido_idpartido_seq'::regclass);


--
-- Name: rol idrol; Type: DEFAULT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.rol ALTER COLUMN idrol SET DEFAULT nextval('public.rol_idrol_seq'::regclass);


--
-- Name: sede idsede; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sede ALTER COLUMN idsede SET DEFAULT nextval('public.sede_idsede_seq'::regclass);


--
-- Name: seleccion idseleccion; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seleccion ALTER COLUMN idseleccion SET DEFAULT nextval('public.seleccion_idseleccion_seq'::regclass);


--
-- Name: usuario idusuario; Type: DEFAULT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.usuario ALTER COLUMN idusuario SET DEFAULT nextval('public.usuario_idusuario_seq'::regclass);


--
-- Data for Name: auditoria; Type: TABLE DATA; Schema: public; Owner: estadisticas_user
--

COPY public.auditoria (idauditoria, tipoaccion, fechahora, tablaafectada, descripcion, idusuario) FROM stdin;
1	createTeam	2026-07-17 19:08:00.072	StatisticsService	Parámetros: [ec.edu.utn.estadisticas.dto.TeamInputDTO@401fafb1]	1
2	registerUser	2026-07-17 19:53:34.403	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@50ff1f16]	\N
3	registerUser	2026-07-17 19:58:36.938	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@52fe4d93]	\N
4	createTeam	2026-07-17 20:42:28.251	StatisticsService	Parámetros: [ec.edu.utn.estadisticas.dto.TeamInputDTO@4e92e943]	2
5	registerUser	2026-07-21 14:39:44.557	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@8aab1ec]	\N
6	registerUser	2026-07-21 15:48:15.234	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@39a76f8]	\N
7	registerUser	2026-07-21 16:24:53.48	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@1959c09e]	\N
8	registerUser	2026-07-21 16:58:51.859	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@316608c7]	\N
9	registerUser	2026-07-21 17:37:34.154	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@73bce837]	\N
42	registerUser	2026-07-22 08:28:45.239	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@43c60fc3]	\N
43	createMatch	2026-07-22 08:36:06.154	StatisticsService	Parámetros: [ec.edu.utn.estadisticas.dto.MatchInputDTO@65b070ca]	2
44	registerResult	2026-07-22 08:58:11.258	StatisticsService	Parámetros: [73, ec.edu.utn.estadisticas.dto.ResultDTO@635d59f9]	2
45	createMatch	2026-07-22 09:06:39.75	StatisticsService	Parámetros: [ec.edu.utn.estadisticas.dto.MatchInputDTO@73c01eb5]	2
46	registerResult	2026-07-22 09:13:19.019	StatisticsService	Parámetros: [74, ec.edu.utn.estadisticas.dto.ResultDTO@65c731ce]	2
47	createMatch	2026-07-22 12:23:16.466	StatisticsService	Parámetros: [ec.edu.utn.estadisticas.dto.MatchInputDTO@4ec78ff8]	2
48	registerResult	2026-07-22 12:25:58.815	StatisticsService	Parámetros: [75, ec.edu.utn.estadisticas.dto.ResultDTO@4d29021d]	2
49	createMatch	2026-07-22 12:43:24.738	StatisticsService	Parámetros: [ec.edu.utn.estadisticas.dto.MatchInputDTO@36e32394]	2
50	registerUser	2026-07-22 14:55:54.646	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@384842be]	\N
51	registerUser	2026-07-22 20:31:25.412	UserService	Parámetros: [ec.edu.utn.estadisticas.dto.UserInputDTO@21e68903]	\N
\.


--
-- Data for Name: ciudad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ciudad (idciudad, nombre, idpais) FROM stdin;
1	Ciudad de México	1
2	Zapopan	1
3	Monterrey	1
4	Toronto	2
5	Vancouver	2
6	East Rutherford	3
7	Inglewood	3
8	Arlington	3
9	Atlanta	3
10	Houston	3
11	Foxborough	3
12	Filadelfia	3
13	Miami Gardens	3
14	Kansas City	3
15	Santa Clara	3
16	Seattle	3
\.


--
-- Data for Name: confederacion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.confederacion (idconfederacion, nombre) FROM stdin;
1	Concacaf
2	AFC
3	CAF
4	UEFA
5	CONMEBOL
6	OFC
\.


--
-- Data for Name: fase; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.fase (idfase, codigo, nombre, fechainicio, fechafin) FROM stdin;
1	GRUPOS	Fase de grupos	2026-06-11	2026-06-27
2	DIECISEISAVOS	Dieciseisavos de final (Ronda de 32)	2026-06-28	2026-07-03
3	OCTAVOS	Octavos de final	2026-07-04	2026-07-07
4	CUARTOS	Cuartos de final	2026-07-09	2026-07-11
5	SEMIFINAL	Semifinales	2026-07-14	2026-07-15
6	TERCER_PUESTO	Tercer puesto	2026-07-18	2026-07-18
7	FINAL	Final (MetLife Stadium, Nueva Jersey)	2026-07-19	2026-07-19
\.


--
-- Data for Name: grupo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.grupo (idgrupo, codigo, nombre) FROM stdin;
1	A	Grupo A
2	B	Grupo B
3	C	Grupo C
4	D	Grupo D
5	E	Grupo E
6	F	Grupo F
7	G	Grupo G
8	H	Grupo H
9	I	Grupo I
10	J	Grupo J
11	K	Grupo K
12	L	Grupo L
\.


--
-- Data for Name: pais; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pais (idpais, nombre) FROM stdin;
1	México
2	Canadá
3	Estados Unidos
\.


--
-- Data for Name: partido; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.partido (idpartido, numeropartidofifa, fechahorautc, estado, goleslocal, golesvisitante, cuotaempate, cuotavisitante, cuotalocal, fecharesultadoregistrado, idfase, idsede, idgrupo, idseleccionlocal, idseleccionvisitante) FROM stdin;
3	3	2026-06-12 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	4	2	5	6
4	4	2026-06-13 01:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	7	4	13	15
5	5	2026-06-13 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	15	2	7	8
6	6	2026-06-13 22:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	6	3	9	10
7	7	2026-06-14 01:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	11	3	11	12
8	8	2026-06-14 04:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	5	4	14	16
9	9	2026-06-14 17:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	10	5	17	20
10	10	2026-06-14 20:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	8	6	21	22
11	11	2026-06-14 23:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	12	5	19	18
12	12	2026-06-15 02:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	3	6	24	23
13	13	2026-06-15 16:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	9	8	29	32
14	14	2026-06-15 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	16	7	25	27
15	15	2026-06-15 22:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	13	8	31	30
16	16	2026-06-16 01:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	7	7	26	28
17	17	2026-06-16 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	6	9	33	34
18	18	2026-06-16 22:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	11	9	36	35
19	19	2026-06-17 01:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	14	10	37	39
20	20	2026-06-17 04:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	15	10	38	40
21	21	2026-06-17 17:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	10	11	41	44
22	22	2026-06-17 20:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	8	12	45	46
23	23	2026-06-17 23:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	4	12	48	47
24	24	2026-06-18 02:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	1	11	43	42
25	25	2026-06-18 16:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	9	1	4	3
26	26	2026-06-18 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	7	2	8	6
27	27	2026-06-18 22:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	5	2	5	7
28	28	2026-06-19 01:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	2	1	1	2
29	29	2026-06-19 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	16	4	13	14
30	30	2026-06-19 22:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	11	3	12	10
31	31	2026-06-20 00:30:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	12	3	9	11
32	32	2026-06-20 03:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	15	4	16	15
33	33	2026-06-20 17:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	10	6	21	24
34	34	2026-06-20 20:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	4	5	17	19
35	35	2026-06-21 00:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	14	5	18	20
36	36	2026-06-21 04:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	3	6	23	22
37	37	2026-06-21 16:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	9	8	29	31
38	38	2026-06-21 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	7	7	25	26
39	39	2026-06-21 22:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	13	8	30	32
40	40	2026-06-22 01:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	5	7	28	27
41	41	2026-06-22 17:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	8	10	37	38
42	42	2026-06-22 21:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	12	9	33	36
43	43	2026-06-23 00:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	6	9	35	34
44	44	2026-06-23 03:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	15	10	40	39
45	45	2026-06-23 17:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	10	11	41	43
46	46	2026-06-23 20:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	11	12	45	48
47	47	2026-06-23 23:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	4	12	47	46
48	48	2026-06-24 02:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	2	11	42	44
49	49	2026-06-24 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	5	2	8	5
50	50	2026-06-24 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	16	2	6	7
51	51	2026-06-24 22:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	13	3	12	9
52	52	2026-06-24 22:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	9	3	10	11
53	53	2026-06-25 01:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	1	1	4	1
54	54	2026-06-25 01:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	3	1	3	2
55	55	2026-06-25 20:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	12	5	20	19
56	56	2026-06-25 20:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	6	5	18	17
57	57	2026-06-25 23:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	8	6	22	24
58	58	2026-06-25 23:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	14	6	23	21
59	59	2026-06-26 02:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	7	4	16	13
60	60	2026-06-26 02:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	15	4	15	14
61	61	2026-06-26 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	11	9	35	33
62	62	2026-06-26 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	4	9	34	36
63	63	2026-06-27 00:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	10	8	32	31
64	64	2026-06-27 00:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	2	8	30	29
65	65	2026-06-27 03:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	16	7	27	26
66	66	2026-06-27 03:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	5	7	28	25
67	67	2026-06-27 21:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	6	12	47	45
68	68	2026-06-27 21:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	12	12	46	48
69	69	2026-06-27 23:30:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	13	11	42	41
70	70	2026-06-27 23:30:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	9	11	44	43
71	71	2026-06-28 02:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	14	10	39	38
72	72	2026-06-28 02:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	8	10	40	37
2	2	2026-06-12 02:00:00	FINALIZADO	1	1	\N	\N	\N	2026-07-13 17:10:27.164	1	2	1	2	4
1	1	2026-06-11 19:00:00	PROGRAMADO	\N	\N	\N	\N	\N	\N	1	1	1	1	3
73	200	2026-07-23 15:00:00	FINALIZADO	1	0	3.2	4.5	1.8	2026-07-22 08:58:11.103	1	5	\N	1	17
74	100	2026-07-23 13:00:00	FINALIZADO	4	2	1.04	1.04	1.04	2026-07-22 09:13:18.984	1	6	10	33	45
75	300	2026-07-23 10:00:00	FINALIZADO	4	3	1.05	1.06	1.05	2026-07-22 12:25:58.146	7	13	\N	41	9
76	76	2026-07-23 13:00:00	PROGRAMADO	\N	\N	1.34	1.03	1.03	\N	5	6	\N	18	1
\.


--
-- Data for Name: rol; Type: TABLE DATA; Schema: public; Owner: estadisticas_user
--

COPY public.rol (idrol, nombre, descripcion) FROM stdin;
1	ADMINISTRADOR	Gestiona torneo, resultados, cuotas y usuarios
2	USUARIO	Consulta información y realiza predicciones con UTNGolCoin
3	INVITADO	Solo consulta calendario, estadísticas y posiciones
\.


--
-- Data for Name: sede; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sede (idsede, estadio, capacidad, idciudad) FROM stdin;
1	Estadio Azteca (Estadio Ciudad de México)	83000	1
2	Estadio Akron (Estadio Guadalajara)	48000	2
3	Estadio BBVA (Estadio Monterrey)	53500	3
4	BMO Field (Toronto Stadium)	45000	4
5	BC Place (Vancouver Stadium)	54000	5
6	MetLife Stadium (New York New Jersey Stadium)	82500	6
7	SoFi Stadium (Los Angeles Stadium)	70000	7
8	AT&T Stadium (Dallas Stadium)	80000	8
9	Mercedes-Benz Stadium (Atlanta Stadium)	71000	9
10	NRG Stadium (Houston Stadium)	72000	10
11	Gillette Stadium (Boston Stadium)	65000	11
12	Lincoln Financial Field (Philadelphia Stadium)	69000	12
13	Hard Rock Stadium (Miami Stadium)	65000	13
14	Arrowhead Stadium (Kansas City Stadium)	76000	14
15	Levi's Stadium (San Francisco Bay Area Stadium)	68500	15
16	Lumen Field (Seattle Stadium)	68000	16
\.


--
-- Data for Name: seleccion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.seleccion (idseleccion, nombre, codigofifa, esanfitrion, clasificacion, partidosjugados, puntos, partidosganados, partidosempatados, partidosperdidos, golesfavor, golescontra, idgrupo, idconfederacion) FROM stdin;
5	Canadá	CAN	t	Anfitrión	0	0	0	0	0	0	0	2	1
6	Bosnia y Herzegovina	BIH	f	Repechaje UEFA (Ruta A)	0	0	0	0	0	0	0	2	4
7	Catar	QAT	f	Clasificación AFC	0	0	0	0	0	0	0	2	2
8	Suiza	SUI	f	Clasificación UEFA	0	0	0	0	0	0	0	2	4
9	Brasil	BRA	f	Clasificación CONMEBOL	0	0	0	0	0	0	0	3	5
10	Marruecos	MAR	f	Clasificación CAF	0	0	0	0	0	0	0	3	3
11	Haití	HAI	f	Clasificación Concacaf	0	0	0	0	0	0	0	3	1
12	Escocia	SCO	f	Clasificación UEFA	0	0	0	0	0	0	0	3	4
13	Estados Unidos	USA	t	Anfitrión	0	0	0	0	0	0	0	4	1
14	Australia	AUS	f	Clasificación AFC	0	0	0	0	0	0	0	4	2
15	Paraguay	PAR	f	Clasificación CONMEBOL	0	0	0	0	0	0	0	4	5
16	Turquía	TUR	f	Repechaje UEFA (Ruta C)	0	0	0	0	0	0	0	4	4
17	Alemania	GER	f	Clasificación UEFA	0	0	0	0	0	0	0	5	4
18	Ecuador	ECU	f	Clasificación CONMEBOL	0	0	0	0	0	0	0	5	5
19	Costa de Marfil	CIV	f	Clasificación CAF	0	0	0	0	0	0	0	5	3
20	Curazao	CUW	f	Clasificación Concacaf	0	0	0	0	0	0	0	5	1
21	Países Bajos	NED	f	Clasificación UEFA	0	0	0	0	0	0	0	6	4
22	Japón	JPN	f	Clasificación AFC	0	0	0	0	0	0	0	6	2
23	Túnez	TUN	f	Clasificación CAF	0	0	0	0	0	0	0	6	3
24	Suecia	SWE	f	Repechaje UEFA (Ruta B)	0	0	0	0	0	0	0	6	4
25	Bélgica	BEL	f	Clasificación UEFA	0	0	0	0	0	0	0	7	4
26	Irán	IRN	f	Clasificación AFC	0	0	0	0	0	0	0	7	2
27	Egipto	EGY	f	Clasificación CAF	0	0	0	0	0	0	0	7	3
28	Nueva Zelanda	NZL	f	Clasificación OFC	0	0	0	0	0	0	0	7	6
29	España	ESP	f	Clasificación UEFA	0	0	0	0	0	0	0	8	4
30	Uruguay	URU	f	Clasificación CONMEBOL	0	0	0	0	0	0	0	8	5
31	Arabia Saudita	KSA	f	Clasificación AFC	0	0	0	0	0	0	0	8	2
32	Cabo Verde	CPV	f	Clasificación CAF	0	0	0	0	0	0	0	8	3
34	Senegal	SEN	f	Clasificación CAF	0	0	0	0	0	0	0	9	3
35	Noruega	NOR	f	Clasificación UEFA	0	0	0	0	0	0	0	9	4
36	Irak	IRQ	f	Repechaje interconfederaciones	0	0	0	0	0	0	0	9	2
37	Argentina	ARG	f	Clasificación CONMEBOL (campeón vigente)	0	0	0	0	0	0	0	10	5
38	Austria	AUT	f	Clasificación UEFA	0	0	0	0	0	0	0	10	4
39	Argelia	ALG	f	Clasificación CAF	0	0	0	0	0	0	0	10	3
40	Jordania	JOR	f	Clasificación AFC	0	0	0	0	0	0	0	10	2
41	Portugal	POR	f	Clasificación UEFA	0	0	0	0	0	0	0	11	4
42	Colombia	COL	f	Clasificación CONMEBOL	0	0	0	0	0	0	0	11	5
43	Uzbekistán	UZB	f	Clasificación AFC	0	0	0	0	0	0	0	11	2
44	RD Congo	COD	f	Repechaje interconfederaciones	0	0	0	0	0	0	0	11	3
46	Croacia	CRO	f	Clasificación UEFA	0	0	0	0	0	0	0	12	4
47	Panamá	PAN	f	Clasificación Concacaf	0	0	0	0	0	0	0	12	1
48	Ghana	GHA	f	Clasificación CAF	0	0	0	0	0	0	0	12	3
2	Corea del Sur	KOR	f	Clasificación AFC	1	1	0	1	0	1	1	1	2
4	Chequia	CZE	f	Repechaje UEFA (Ruta D)	1	1	0	1	0	1	1	1	4
1	México	MEX	t	Anfitrión	1	3	1	0	0	2	1	1	1
3	Sudáfrica	RSA	f	Clasificación CAF	1	0	0	0	1	1	2	1	3
45	Inglaterra	ENG	f	Clasificación UEFA	1	0	0	0	1	2	4	12	4
33	Francia	FRA	f	Clasificación UEFA	1	3	1	0	0	4	2	9	4
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: estadisticas_user
--

COPY public.usuario (idusuario, nombre, email, username, passwordhash, estado, fecharegistro, ultimoacceso, idrol) FROM stdin;
43	adolfo	adolfo@gmail.com	adolfito	$2a$10$Q4N0sn0t/mKaOzXLLE2NfeOr/YVlu6CjJL5wz7hMyGM2BjoWaPtSG	t	2026-07-22 20:31:25.266	2026-07-22 20:31:38.702	2
1	Usuario Prueba	prueba@utn.edu.ec	uprueba	$2a$10$i4VHe24SFm8mPIxAogzNxeYEEAcLwdgxBGPB3USikkyiKFWMQefaS	t	2026-07-17 17:06:24.601	2026-07-17 17:46:51.08	2
3	Usuario Normal	normal@utn.edu.ec	normalito	$2a$10$hMhr74E7yIXG3BLcun8Xhe.cUNa/26XWX3TTN7qTRi9ooEpYb2giO	t	2026-07-17 19:58:36.931	2026-07-21 15:45:25.722	2
5	Ariel	ariel@gmail.com	Elpro	$2a$10$bGVupFS9bF3L.afHwzW9lOYMTV8ZlOL9RmHg/r9bTQ9w3JjUIxoUu	t	2026-07-21 15:48:15.16	2026-07-21 15:48:30.245	2
6	elkin	elkin@gmail.com	elelkin	$2a$10$oRVi6Vvv4AOLF8lXTSej3eSarwGG.NLG9ixMQEIPU05DCqEa7JuHW	t	2026-07-21 16:24:53.469	2026-07-21 16:25:05.919	2
7	pepito	pepito@gmail.com	pepi	$2a$10$k6qiNAES3PQ6Gc0G4YwuRebbccWjwCbgnjQJV9YPm7rHlOKZMYW1u	t	2026-07-21 16:58:51.774	2026-07-21 16:59:03.439	2
8	Eimy	eimy@gmail.com	Ami	$2a$10$mdjSfpbqlew27yZV73ZO/uPa0DweEV8C3J9dY3/veQFIJ1mMRb3tm	t	2026-07-21 17:37:34.123	2026-07-21 17:38:57.231	2
42	Axel	axel@gmail.com	axeli	$2a$10$4pT//c.9NGW4H842Gl2U..5sFoLEcqaLoBeoHmK/5P6h6EdCLgXFu	t	2026-07-22 14:55:54.488	2026-07-22 14:56:07.69	2
41	David 	david@gmail.com	Davidddd	$2a$10$SfUhCcaVX8apLpRsf/e6ZuGNrtXeQxmUe78z/uaQL8EM1wVB.THRW	t	2026-07-22 08:28:45.034	2026-07-22 16:35:35.578	2
2	Administrador del Torneo	admin@utngolmundial.edu.ec	admin	$2a$10$EHUW925YSrg8SvOTh97S.umAe2eNSseDMdr5Ia0zD0nuTaMY2CydW	t	2026-07-17 19:53:34.379	2026-07-22 17:57:47.423	1
4	Yurak Sinchico	yurianrango3@gmail.com	Yuri	$2a$10$03bJifjaZed5V2HwX.9oAe8I9w/sd0BYafcIn1FfQzOUmmy/0Ciq6	t	2026-07-21 14:39:44.412	2026-07-22 20:22:07.803	2
\.


--
-- Name: auditoria_idauditoria_seq; Type: SEQUENCE SET; Schema: public; Owner: estadisticas_user
--

SELECT pg_catalog.setval('public.auditoria_idauditoria_seq', 51, true);


--
-- Name: ciudad_idciudad_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ciudad_idciudad_seq', 16, true);


--
-- Name: confederacion_idconfederacion_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.confederacion_idconfederacion_seq', 6, true);


--
-- Name: fase_idfase_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.fase_idfase_seq', 7, true);


--
-- Name: grupo_idgrupo_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.grupo_idgrupo_seq', 12, true);


--
-- Name: pais_idpais_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pais_idpais_seq', 3, true);


--
-- Name: partido_idpartido_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.partido_idpartido_seq', 76, true);


--
-- Name: rol_idrol_seq; Type: SEQUENCE SET; Schema: public; Owner: estadisticas_user
--

SELECT pg_catalog.setval('public.rol_idrol_seq', 3, true);


--
-- Name: sede_idsede_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.sede_idsede_seq', 16, true);


--
-- Name: seleccion_idseleccion_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seleccion_idseleccion_seq', 53, true);


--
-- Name: usuario_idusuario_seq; Type: SEQUENCE SET; Schema: public; Owner: estadisticas_user
--

SELECT pg_catalog.setval('public.usuario_idusuario_seq', 43, true);


--
-- Name: auditoria auditoria_pkey; Type: CONSTRAINT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.auditoria
    ADD CONSTRAINT auditoria_pkey PRIMARY KEY (idauditoria);


--
-- Name: ciudad ciudad_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ciudad
    ADD CONSTRAINT ciudad_nombre_key UNIQUE (nombre);


--
-- Name: ciudad ciudad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ciudad
    ADD CONSTRAINT ciudad_pkey PRIMARY KEY (idciudad);


--
-- Name: confederacion confederacion_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confederacion
    ADD CONSTRAINT confederacion_nombre_key UNIQUE (nombre);


--
-- Name: confederacion confederacion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.confederacion
    ADD CONSTRAINT confederacion_pkey PRIMARY KEY (idconfederacion);


--
-- Name: fase fase_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.fase
    ADD CONSTRAINT fase_pkey PRIMARY KEY (idfase);


--
-- Name: grupo grupo_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grupo
    ADD CONSTRAINT grupo_codigo_key UNIQUE (codigo);


--
-- Name: grupo grupo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.grupo
    ADD CONSTRAINT grupo_pkey PRIMARY KEY (idgrupo);


--
-- Name: pais pais_nombre_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais
    ADD CONSTRAINT pais_nombre_key UNIQUE (nombre);


--
-- Name: pais pais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais
    ADD CONSTRAINT pais_pkey PRIMARY KEY (idpais);


--
-- Name: partido partido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido
    ADD CONSTRAINT partido_pkey PRIMARY KEY (idpartido);


--
-- Name: rol rol_nombre_key; Type: CONSTRAINT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_nombre_key UNIQUE (nombre);


--
-- Name: rol rol_pkey; Type: CONSTRAINT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.rol
    ADD CONSTRAINT rol_pkey PRIMARY KEY (idrol);


--
-- Name: sede sede_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sede
    ADD CONSTRAINT sede_pkey PRIMARY KEY (idsede);


--
-- Name: seleccion seleccion_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seleccion
    ADD CONSTRAINT seleccion_pkey PRIMARY KEY (idseleccion);


--
-- Name: usuario usuario_email_key; Type: CONSTRAINT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_email_key UNIQUE (email);


--
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (idusuario);


--
-- Name: usuario usuario_username_key; Type: CONSTRAINT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_username_key UNIQUE (username);


--
-- Name: auditoria auditoria_idusuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.auditoria
    ADD CONSTRAINT auditoria_idusuario_fkey FOREIGN KEY (idusuario) REFERENCES public.usuario(idusuario);


--
-- Name: ciudad ciudad_idpais_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ciudad
    ADD CONSTRAINT ciudad_idpais_fkey FOREIGN KEY (idpais) REFERENCES public.pais(idpais);


--
-- Name: partido partido_idfase_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido
    ADD CONSTRAINT partido_idfase_fkey FOREIGN KEY (idfase) REFERENCES public.fase(idfase);


--
-- Name: partido partido_idgrupo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido
    ADD CONSTRAINT partido_idgrupo_fkey FOREIGN KEY (idgrupo) REFERENCES public.grupo(idgrupo);


--
-- Name: partido partido_idsede_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido
    ADD CONSTRAINT partido_idsede_fkey FOREIGN KEY (idsede) REFERENCES public.sede(idsede);


--
-- Name: partido partido_idseleccionlocal_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido
    ADD CONSTRAINT partido_idseleccionlocal_fkey FOREIGN KEY (idseleccionlocal) REFERENCES public.seleccion(idseleccion);


--
-- Name: partido partido_idseleccionvisitante_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partido
    ADD CONSTRAINT partido_idseleccionvisitante_fkey FOREIGN KEY (idseleccionvisitante) REFERENCES public.seleccion(idseleccion);


--
-- Name: sede sede_idciudad_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sede
    ADD CONSTRAINT sede_idciudad_fkey FOREIGN KEY (idciudad) REFERENCES public.ciudad(idciudad);


--
-- Name: seleccion seleccion_idconfederacion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seleccion
    ADD CONSTRAINT seleccion_idconfederacion_fkey FOREIGN KEY (idconfederacion) REFERENCES public.confederacion(idconfederacion);


--
-- Name: seleccion seleccion_idgrupo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seleccion
    ADD CONSTRAINT seleccion_idgrupo_fkey FOREIGN KEY (idgrupo) REFERENCES public.grupo(idgrupo);


--
-- Name: usuario usuario_idrol_fkey; Type: FK CONSTRAINT; Schema: public; Owner: estadisticas_user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_idrol_fkey FOREIGN KEY (idrol) REFERENCES public.rol(idrol);


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO estadisticas_user;


--
-- Name: TABLE ciudad; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.ciudad TO estadisticas_user;


--
-- Name: SEQUENCE ciudad_idciudad_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.ciudad_idciudad_seq TO estadisticas_user;


--
-- Name: TABLE confederacion; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.confederacion TO estadisticas_user;


--
-- Name: SEQUENCE confederacion_idconfederacion_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.confederacion_idconfederacion_seq TO estadisticas_user;


--
-- Name: TABLE fase; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.fase TO estadisticas_user;


--
-- Name: SEQUENCE fase_idfase_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.fase_idfase_seq TO estadisticas_user;


--
-- Name: TABLE grupo; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.grupo TO estadisticas_user;


--
-- Name: SEQUENCE grupo_idgrupo_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.grupo_idgrupo_seq TO estadisticas_user;


--
-- Name: TABLE pais; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.pais TO estadisticas_user;


--
-- Name: SEQUENCE pais_idpais_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.pais_idpais_seq TO estadisticas_user;


--
-- Name: TABLE partido; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.partido TO estadisticas_user;


--
-- Name: SEQUENCE partido_idpartido_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.partido_idpartido_seq TO estadisticas_user;


--
-- Name: TABLE sede; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.sede TO estadisticas_user;


--
-- Name: SEQUENCE sede_idsede_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.sede_idsede_seq TO estadisticas_user;


--
-- Name: TABLE seleccion; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON TABLE public.seleccion TO estadisticas_user;


--
-- Name: SEQUENCE seleccion_idseleccion_seq; Type: ACL; Schema: public; Owner: postgres
--

GRANT ALL ON SEQUENCE public.seleccion_idseleccion_seq TO estadisticas_user;


--
-- PostgreSQL database dump complete
--

\unrestrict n0JB798g8yLznWl0VlvjdvBH6nUHlg1DjEHyY0gd53wvBNw5T0s9z8478GqAyL3

