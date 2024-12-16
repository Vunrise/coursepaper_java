--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2024-12-02 23:16:20

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
-- TOC entry 222 (class 1259 OID 16853)
-- Name: birth_certificate; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.birth_certificate (
    birth_id integer NOT NULL,
    citizen_id integer,
    birth_first_name character varying(100) NOT NULL,
    birth_last_name character varying(100) NOT NULL,
    birth_middle_name character varying(100),
    mother_id integer,
    father_id integer,
    birth_date date NOT NULL,
    birth_place character varying(255) NOT NULL,
    registration_date date NOT NULL,
    status character varying(10) NOT NULL,
    CONSTRAINT birth_certificate_birth_date_check CHECK ((birth_date <= CURRENT_DATE)),
    CONSTRAINT birth_certificate_registration_date_check CHECK ((registration_date <= CURRENT_DATE)),
    CONSTRAINT birth_certificate_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying])::text[])))
);


ALTER TABLE public.birth_certificate OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16852)
-- Name: birth_certificate_birth_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.birth_certificate_birth_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.birth_certificate_birth_id_seq OWNER TO postgres;

--
-- TOC entry 4946 (class 0 OID 0)
-- Dependencies: 221
-- Name: birth_certificate_birth_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.birth_certificate_birth_id_seq OWNED BY public.birth_certificate.birth_id;


--
-- TOC entry 220 (class 1259 OID 16842)
-- Name: citizens; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.citizens (
    citizen_id integer NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL,
    middle_name character varying(100),
    birth_date date NOT NULL,
    birth_place character varying(255) NOT NULL,
    gender character varying(10) NOT NULL,
    address character varying(255) NOT NULL,
    status character varying(10) NOT NULL,
    CONSTRAINT citizens_gender_check CHECK (((gender)::text = ANY ((ARRAY['мужской'::character varying, 'женский'::character varying])::text[]))),
    CONSTRAINT citizens_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying])::text[])))
);


ALTER TABLE public.citizens OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16841)
-- Name: citizens_citizen_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.citizens_citizen_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.citizens_citizen_id_seq OWNER TO postgres;

--
-- TOC entry 4947 (class 0 OID 0)
-- Dependencies: 219
-- Name: citizens_citizen_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.citizens_citizen_id_seq OWNED BY public.citizens.citizen_id;


--
-- TOC entry 228 (class 1259 OID 16920)
-- Name: death_registration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.death_registration (
    death_id integer NOT NULL,
    citizen_id integer NOT NULL,
    death_date date NOT NULL,
    death_place character varying(255) NOT NULL,
    registration_date date NOT NULL,
    status character varying(10) NOT NULL,
    CONSTRAINT death_registration_death_date_check CHECK ((death_date <= CURRENT_DATE)),
    CONSTRAINT death_registration_registration_date_check CHECK ((registration_date <= CURRENT_DATE)),
    CONSTRAINT death_registration_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying])::text[])))
);


ALTER TABLE public.death_registration OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16919)
-- Name: death_registration_death_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.death_registration_death_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.death_registration_death_id_seq OWNER TO postgres;

--
-- TOC entry 4948 (class 0 OID 0)
-- Dependencies: 227
-- Name: death_registration_death_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.death_registration_death_id_seq OWNED BY public.death_registration.death_id;


--
-- TOC entry 226 (class 1259 OID 16905)
-- Name: divorce_registration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.divorce_registration (
    divorce_id integer NOT NULL,
    marriage_id integer NOT NULL,
    divorce_date date NOT NULL,
    registration_date date NOT NULL,
    status character varying(10) NOT NULL,
    CONSTRAINT divorce_registration_divorce_date_check CHECK ((divorce_date <= CURRENT_DATE)),
    CONSTRAINT divorce_registration_registration_date_check CHECK ((registration_date <= CURRENT_DATE)),
    CONSTRAINT divorce_registration_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying])::text[])))
);


ALTER TABLE public.divorce_registration OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16904)
-- Name: divorce_registration_divorce_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.divorce_registration_divorce_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.divorce_registration_divorce_id_seq OWNER TO postgres;

--
-- TOC entry 4949 (class 0 OID 0)
-- Dependencies: 225
-- Name: divorce_registration_divorce_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.divorce_registration_divorce_id_seq OWNED BY public.divorce_registration.divorce_id;


--
-- TOC entry 217 (class 1259 OID 16517)
-- Name: employees; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employees (
    username text NOT NULL,
    password text NOT NULL
);


ALTER TABLE public.employees OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16885)
-- Name: marriage_registration; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.marriage_registration (
    marriage_id integer NOT NULL,
    wife_id integer NOT NULL,
    husband_id integer NOT NULL,
    marriage_date date NOT NULL,
    marriage_place character varying(255) NOT NULL,
    registration_date date NOT NULL,
    marriage_last_name_wife character varying(100),
    marriage_last_name_husband character varying(100),
    status character varying(10) NOT NULL,
    CONSTRAINT marriage_registration_marriage_date_check CHECK ((marriage_date <= CURRENT_DATE)),
    CONSTRAINT marriage_registration_registration_date_check CHECK ((registration_date <= CURRENT_DATE)),
    CONSTRAINT marriage_registration_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying])::text[])))
);


ALTER TABLE public.marriage_registration OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16884)
-- Name: marriage_registration_marriage_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.marriage_registration_marriage_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.marriage_registration_marriage_id_seq OWNER TO postgres;

--
-- TOC entry 4950 (class 0 OID 0)
-- Dependencies: 223
-- Name: marriage_registration_marriage_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.marriage_registration_marriage_id_seq OWNED BY public.marriage_registration.marriage_id;


--
-- TOC entry 230 (class 1259 OID 16935)
-- Name: name_change; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.name_change (
    name_change_id integer NOT NULL,
    citizen_id integer NOT NULL,
    old_name character varying(255) NOT NULL,
    new_name character varying(255) NOT NULL,
    change_date date NOT NULL,
    registration_date date NOT NULL,
    status character varying(10) NOT NULL,
    CONSTRAINT name_change_change_date_check CHECK ((change_date <= CURRENT_DATE)),
    CONSTRAINT name_change_registration_date_check CHECK ((registration_date <= CURRENT_DATE)),
    CONSTRAINT name_change_status_check CHECK (((status)::text = ANY ((ARRAY['active'::character varying, 'inactive'::character varying])::text[])))
);


ALTER TABLE public.name_change OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16934)
-- Name: name_change_name_change_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.name_change_name_change_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.name_change_name_change_id_seq OWNER TO postgres;

--
-- TOC entry 4951 (class 0 OID 0)
-- Dependencies: 229
-- Name: name_change_name_change_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.name_change_name_change_id_seq OWNED BY public.name_change.name_change_id;


--
-- TOC entry 218 (class 1259 OID 16527)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    username text NOT NULL,
    password text NOT NULL,
    citizen_id integer NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4729 (class 2604 OID 16856)
-- Name: birth_certificate birth_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.birth_certificate ALTER COLUMN birth_id SET DEFAULT nextval('public.birth_certificate_birth_id_seq'::regclass);


--
-- TOC entry 4728 (class 2604 OID 16845)
-- Name: citizens citizen_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citizens ALTER COLUMN citizen_id SET DEFAULT nextval('public.citizens_citizen_id_seq'::regclass);


--
-- TOC entry 4732 (class 2604 OID 16923)
-- Name: death_registration death_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.death_registration ALTER COLUMN death_id SET DEFAULT nextval('public.death_registration_death_id_seq'::regclass);


--
-- TOC entry 4731 (class 2604 OID 16908)
-- Name: divorce_registration divorce_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.divorce_registration ALTER COLUMN divorce_id SET DEFAULT nextval('public.divorce_registration_divorce_id_seq'::regclass);


--
-- TOC entry 4730 (class 2604 OID 16888)
-- Name: marriage_registration marriage_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marriage_registration ALTER COLUMN marriage_id SET DEFAULT nextval('public.marriage_registration_marriage_id_seq'::regclass);


--
-- TOC entry 4733 (class 2604 OID 16938)
-- Name: name_change name_change_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.name_change ALTER COLUMN name_change_id SET DEFAULT nextval('public.name_change_name_change_id_seq'::regclass);


--
-- TOC entry 4932 (class 0 OID 16853)
-- Dependencies: 222
-- Data for Name: birth_certificate; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.birth_certificate (birth_id, citizen_id, birth_first_name, birth_last_name, birth_middle_name, mother_id, father_id, birth_date, birth_place, registration_date, status) FROM stdin;
20	43	Алексей	Петров	Игоревич	41	42	2000-04-25	г. Москва	2000-04-26	active
21	44	Екатерина	Смирнова	Валентиновна	\N	\N	2000-07-20	г. Москва	2000-07-21	active
22	41	Марина	Петрова	Ивановна	\N	\N	1978-05-12	г. Москва	1978-05-13	active
23	42	Игорь	Петров	Николаевич	\N	\N	1979-03-14	г. Балашиха	1979-03-15	active
26	47	Иван	Иванов	Иванович	\N	\N	1990-02-15	г. Москва	1990-02-16	active
27	48	Мария	Петрова	Алексеевна	\N	\N	1992-05-20	г. Санкт-Петербург	1992-05-21	active
28	49	Александр	Сидоров	Петрович	\N	\N	1985-07-30	г. Казань	1985-07-31	active
29	50	Анна	Ларина	Михайловна	\N	\N	1998-10-10	г. Новосибирск	1998-10-11	active
30	51	Дмитрий	Николаев	Юрьевич	\N	\N	1980-12-01	г. Екатеринбург	1980-12-02	active
31	52	Елена	Голубева	Игоревна	\N	\N	1986-09-15	г. Челябинск	1986-09-16	active
32	53	Сергей	Федоров	Анатольевич	\N	\N	1991-03-25	г. Нижний Новгород	1991-03-26	active
34	55	Николай	Захаров	Владимирович	\N	\N	1987-01-10	г. Краснодар	1987-01-11	inactive
35	56	Екатерина	Михайлова	Вячеславовна	\N	\N	2000-04-12	г. Ростов-на-Дону	2000-04-13	inactive
36	57	Константин	Иванов	Максимович	\N	\N	1983-11-18	г. Саратов	1983-11-19	active
38	59	Андрей	Орлов	Геннадиевич	\N	\N	1984-06-05	г. Пермь	1984-06-06	active
39	60	Татьяна	Смирнова	Николаевна	\N	\N	1996-01-15	г. Уфа	1996-01-16	active
40	61	Максим	Шмидт	Артурович	\N	\N	1987-11-30	г. Волгоград	1987-12-01	active
33	54	Ольга	Васильева	Станиславовна	\N	\N	1995-08-30	г. Воронеж	1995-08-31	active
37	58	Юлия	Павлова	Ильинична	\N	\N	1989-02-22	г. Омск	1989-02-23	active
\.


--
-- TOC entry 4930 (class 0 OID 16842)
-- Dependencies: 220
-- Data for Name: citizens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.citizens (citizen_id, first_name, last_name, middle_name, birth_date, birth_place, gender, address, status) FROM stdin;
42	Игорь	Петров	Николаевич	1979-03-14	г. Балашиха	мужской	г. Москва, ул. Академика Королева	inactive
41	Марина	Петрова	Ивановна	1978-05-12	г. Москва	женский	г. Москва, ул. Академика Королева	active
44	Петрова	Петрова	Валентиновна	2000-07-20	г. Москва	женский	г. Москва, ул. Грузинский Вал	active
43	Алексей	Петров	Игоревич	2000-04-25	г. Москва	мужской	г. Москва, ул. Москворецкая	active
47	Иван	Иванов	Иванович	1990-02-15	г. Москва	мужской	ул. Пушкина, д. 1	active
48	Мария	Петрова	Алексеевна	1992-05-20	г. Санкт-Петербург	женский	ул. Ленина, д. 2	active
49	Александр	Сидоров	Петрович	1985-07-30	г. Казань	мужской	ул. Советская, д. 3	active
50	Анна	Ларина	Михайловна	1998-10-10	г. Новосибирск	женский	ул. Строителей, д. 4	active
51	Дмитрий	Николаев	Юрьевич	1980-12-01	г. Екатеринбург	мужской	ул. Чапаева, д. 5	active
52	Елена	Голубева	Игоревна	1986-09-15	г. Челябинск	женский	ул. Космонавтов, д. 6	active
53	Сергей	Федоров	Анатольевич	1991-03-25	г. Нижний Новгород	мужской	ул. К.Маркса, д. 7	active
55	Николай	Захаров	Владимирович	1987-01-10	г. Краснодар	мужской	ул. Гагарина, д. 9	inactive
56	Екатерина	Михайлова	Вячеславовна	2000-04-12	г. Ростов-на-Дону	женский	ул. Багратиона, д. 10	inactive
57	Константин	Иванов	Максимович	1983-11-18	г. Саратов	мужской	ул. Лермонтова, д. 11	active
59	Андрей	Орлов	Геннадиевич	1984-06-05	г. Пермь	мужской	ул. Уральская, д. 13	active
60	Татьяна	Смирнова	Николаевна	1996-01-15	г. Уфа	женский	ул. Советская, д. 14	active
61	Максим	Шмидт	Артурович	1987-11-30	г. Волгоград	мужской	ул. Бульварная, д. 15	active
54	Ольга	Федорова	Станиславовна	1995-08-30	г. Воронеж	женский	ул. Кирова, д. 8	active
58	Юлия	Орлова	Ильинична	1989-02-22	г. Омск	женский	ул. Карла Маркса, д. 12	active
\.


--
-- TOC entry 4938 (class 0 OID 16920)
-- Dependencies: 228
-- Data for Name: death_registration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.death_registration (death_id, citizen_id, death_date, death_place, registration_date, status) FROM stdin;
1	42	2022-11-15	г. Москва	2022-11-16	active
4	56	2022-11-01	г. Ростов-на-Дону	2022-11-02	active
3	55	2020-05-15	г. Краснодар	2020-05-16	active
\.


--
-- TOC entry 4936 (class 0 OID 16905)
-- Dependencies: 226
-- Data for Name: divorce_registration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.divorce_registration (divorce_id, marriage_id, divorce_date, registration_date, status) FROM stdin;
9	9	2024-01-10	2024-02-11	active
\.


--
-- TOC entry 4927 (class 0 OID 16517)
-- Dependencies: 217
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employees (username, password) FROM stdin;
employee_1	password_1
1	1
\.


--
-- TOC entry 4934 (class 0 OID 16885)
-- Dependencies: 224
-- Data for Name: marriage_registration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.marriage_registration (marriage_id, wife_id, husband_id, marriage_date, marriage_place, registration_date, marriage_last_name_wife, marriage_last_name_husband, status) FROM stdin;
11	41	42	1999-06-01	г. Москва	1999-06-02	Петрова	Петров	active
9	44	43	2023-05-15	г. Москва	2023-05-16	Петрова	Петров	inactive
15	44	43	2024-06-06	Moscow	2024-11-27	Петрова	Петров	active
16	48	49	2020-05-20	г. Москва	2020-05-21	Петрова	Сидоров	active
17	54	53	2021-08-15	г. Санкт-Петербург	2021-08-16	Федорова	Федоров	active
18	58	59	2019-03-10	г. Омск	2019-03-11	Орлова	Орлов	active
\.


--
-- TOC entry 4940 (class 0 OID 16935)
-- Dependencies: 230
-- Data for Name: name_change; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.name_change (name_change_id, citizen_id, old_name, new_name, change_date, registration_date, status) FROM stdin;
11	44	Петрова Екатерина Валентиновна	Смирнова Екатерина Валентиновна	2023-05-15	2023-05-16	active
\.


--
-- TOC entry 4928 (class 0 OID 16527)
-- Dependencies: 218
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (username, password, citizen_id) FROM stdin;
aleksey_petrov	password_3	43
ekaterina_smirnova	password_4	44
marina_petrova	password_1	41
ivan1990	password1	47
maria1992	password2	48
alexander1985	password3	49
anna1998	password4	50
dmitriy1980	password5	51
elena1986	password6	52
sergey1991	password7	53
olga1995	password8	54
konstantin1983	password9	57
yulia1989	password10	58
andrey1984	password11	59
tatiana1996	password12	60
maksim1987	password13	61
\.


--
-- TOC entry 4952 (class 0 OID 0)
-- Dependencies: 221
-- Name: birth_certificate_birth_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.birth_certificate_birth_id_seq', 40, true);


--
-- TOC entry 4953 (class 0 OID 0)
-- Dependencies: 219
-- Name: citizens_citizen_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.citizens_citizen_id_seq', 61, true);


--
-- TOC entry 4954 (class 0 OID 0)
-- Dependencies: 227
-- Name: death_registration_death_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.death_registration_death_id_seq', 4, true);


--
-- TOC entry 4955 (class 0 OID 0)
-- Dependencies: 225
-- Name: divorce_registration_divorce_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.divorce_registration_divorce_id_seq', 11, true);


--
-- TOC entry 4956 (class 0 OID 0)
-- Dependencies: 223
-- Name: marriage_registration_marriage_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.marriage_registration_marriage_id_seq', 18, true);


--
-- TOC entry 4957 (class 0 OID 0)
-- Dependencies: 229
-- Name: name_change_name_change_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.name_change_name_change_id_seq', 12, true);


--
-- TOC entry 4762 (class 2606 OID 16984)
-- Name: birth_certificate birth_certificate_citizen_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.birth_certificate
    ADD CONSTRAINT birth_certificate_citizen_id_key UNIQUE (citizen_id);


--
-- TOC entry 4764 (class 2606 OID 16863)
-- Name: birth_certificate birth_certificate_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.birth_certificate
    ADD CONSTRAINT birth_certificate_pkey PRIMARY KEY (birth_id);


--
-- TOC entry 4760 (class 2606 OID 16851)
-- Name: citizens citizens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.citizens
    ADD CONSTRAINT citizens_pkey PRIMARY KEY (citizen_id);


--
-- TOC entry 4770 (class 2606 OID 16928)
-- Name: death_registration death_registration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.death_registration
    ADD CONSTRAINT death_registration_pkey PRIMARY KEY (death_id);


--
-- TOC entry 4768 (class 2606 OID 16913)
-- Name: divorce_registration divorce_registration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.divorce_registration
    ADD CONSTRAINT divorce_registration_pkey PRIMARY KEY (divorce_id);


--
-- TOC entry 4752 (class 2606 OID 16555)
-- Name: employees employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT employee_pkey PRIMARY KEY (username);


--
-- TOC entry 4766 (class 2606 OID 16893)
-- Name: marriage_registration marriage_registration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marriage_registration
    ADD CONSTRAINT marriage_registration_pkey PRIMARY KEY (marriage_id);


--
-- TOC entry 4772 (class 2606 OID 16945)
-- Name: name_change name_change_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.name_change
    ADD CONSTRAINT name_change_pkey PRIMARY KEY (name_change_id);


--
-- TOC entry 4756 (class 2606 OID 16537)
-- Name: users user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_pkey PRIMARY KEY (username);


--
-- TOC entry 4754 (class 2606 OID 16553)
-- Name: employees username; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employees
    ADD CONSTRAINT username UNIQUE (username);


--
-- TOC entry 4758 (class 2606 OID 16662)
-- Name: users users_citizen_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_citizen_id_key UNIQUE (citizen_id);


--
-- TOC entry 4774 (class 2606 OID 16864)
-- Name: birth_certificate birth_certificate_citizen_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.birth_certificate
    ADD CONSTRAINT birth_certificate_citizen_id_fkey FOREIGN KEY (citizen_id) REFERENCES public.citizens(citizen_id);


--
-- TOC entry 4775 (class 2606 OID 16874)
-- Name: birth_certificate birth_certificate_father_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.birth_certificate
    ADD CONSTRAINT birth_certificate_father_id_fkey FOREIGN KEY (father_id) REFERENCES public.citizens(citizen_id);


--
-- TOC entry 4776 (class 2606 OID 16869)
-- Name: birth_certificate birth_certificate_mother_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.birth_certificate
    ADD CONSTRAINT birth_certificate_mother_id_fkey FOREIGN KEY (mother_id) REFERENCES public.citizens(citizen_id);


--
-- TOC entry 4780 (class 2606 OID 16929)
-- Name: death_registration death_registration_citizen_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.death_registration
    ADD CONSTRAINT death_registration_citizen_id_fkey FOREIGN KEY (citizen_id) REFERENCES public.citizens(citizen_id);


--
-- TOC entry 4779 (class 2606 OID 16914)
-- Name: divorce_registration divorce_registration_marriage_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.divorce_registration
    ADD CONSTRAINT divorce_registration_marriage_id_fkey FOREIGN KEY (marriage_id) REFERENCES public.marriage_registration(marriage_id);


--
-- TOC entry 4773 (class 2606 OID 16956)
-- Name: users fk_citizen_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_citizen_id FOREIGN KEY (citizen_id) REFERENCES public.citizens(citizen_id) ON DELETE CASCADE;


--
-- TOC entry 4777 (class 2606 OID 16899)
-- Name: marriage_registration marriage_registration_husband_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marriage_registration
    ADD CONSTRAINT marriage_registration_husband_id_fkey FOREIGN KEY (husband_id) REFERENCES public.citizens(citizen_id);


--
-- TOC entry 4778 (class 2606 OID 16894)
-- Name: marriage_registration marriage_registration_wife_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.marriage_registration
    ADD CONSTRAINT marriage_registration_wife_id_fkey FOREIGN KEY (wife_id) REFERENCES public.citizens(citizen_id);


--
-- TOC entry 4781 (class 2606 OID 16946)
-- Name: name_change name_change_citizen_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.name_change
    ADD CONSTRAINT name_change_citizen_id_fkey FOREIGN KEY (citizen_id) REFERENCES public.citizens(citizen_id);


-- Completed on 2024-12-02 23:16:23

--
-- PostgreSQL database dump complete
--

