'use client';

import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import MainLayout from "@/components/layout/MainLayout";
import {
    Home, Users, Building, Calculator, FileText, AlertTriangle,
    Download, Github, Linkedin, GraduationCap,
    Code, Database, Server, Globe, Shield, Triangle
} from "lucide-react";
import Link from "next/link";
import postmanCollection from "@/data/kitnet.postman_collectionV21.json";

const DocumentationContent = () => {
    const downloadPostmanCollection = () => {

        const dataStr = JSON.stringify(postmanCollection, null, 2);
        const dataUri = `data:application/json;charset=utf-8,${encodeURIComponent(dataStr)}`;

        const exportFileDefaultName = 'Casa_Alegria_API_Collection.json';

        const linkElement = document.createElement('a');
        linkElement.setAttribute('href', dataUri);
        linkElement.setAttribute('download', exportFileDefaultName);
        linkElement.click();
    };

    const techStack = [
        { name: "React", icon: <Code className="h-5 w-5" />, description: "Framework frontend" },
        { name: "TypeScript", icon: <Code className="h-5 w-5" />, description: "Linguagem tipada" },
        { name: "Tailwind CSS", icon: <Globe className="h-5 w-5" />, description: "Framework CSS" },
        { name: "Next", icon: <Triangle fill="#e56b4e" className="h-5 w-5" />, description: "Build tool" },
        { name: "Spring Boot", icon: <Server className="h-5 w-5" />, description: "Backend Java" },
        { name: "MySQL", icon: <Database className="h-5 w-5" />, description: "Banco de dados" },
        { name: "Docker", icon: <Server className="h-5 w-5" />, description: "Containerização" },
        { name: "Firebase Auth", icon: <Shield className="h-5 w-5" />, description: "Autenticação" }
    ];

    const developers = [
        {
            name: "Charles Eduardo",
            linkedin: "https://www.linkedin.com/in/eduardomg12/",
            github: "https://github.com/EduardoMG12",
            role: "Desenvolvedor Full-Stack",
            description: "Especialista em desenvolvimento web com foco em React, Nest e Spring Boot"
        },
        {
            name: "Willian Fragata",
            linkedin: "#",
            github: "#",
            role: "Desenvolvedor Full-Stack",
            description: "Desenvolvedor com experiência em arquitetura de sistemas e banco de dados"
        }
    ];

    const professors = [
        {
            name: "Eduardo Alba",
            linkedin: "https://www.linkedin.com/in/eduardo-luiz-alba-ab373a166/",
            subject: "SIS05502 - Programação de Software",
            description: "Professor responsável pela disciplina de Programação de Software"
        },
        {
            name: "Marina Girolimetto",
            linkedin: "https://www.linkedin.com/in/marina-girolimetto-93b8b3b3/",
            subject: "SIS05501 - Análise e Projeto de Sistemas",
            description: "Professora responsável pela disciplina de Análise e Projeto de Sistemas"
        }
    ];
    const routes = [
        {
            category: "Páginas Principais",
            icon: <Home className="h-5 w-5" />,
            routes: [
                { path: "/", name: "Página Inicial", description: "Landing page da Casa Alegria" },
                { path: "/avalible-properties", name: "Propriedades", description: "Lista de imóveis disponíveis" },
                { path: "/guides", name: "Guias", description: "Central de guias e recursos" },
            ]
        },
        {
            category: "Perfis de Usuário",
            icon: <Users className="h-5 w-5" />,
            routes: [
                { path: "/home-tenant", name: "Home Inquilino", description: "Dashboard do inquilino" },
                { path: "/home-landlord", name: "Home Proprietário", description: "Dashboard do proprietário" },
                { path: "/home-broker", name: "Home Corretor", description: "Dashboard do corretor" },
                { path: "/profile/tenant/1", name: "Perfil Inquilino", description: "Exemplo de perfil de inquilino" },
                { path: "/profile/landlord/1", name: "Perfil Proprietário", description: "Exemplo de perfil de proprietário" },
                { path: "/profile/broker/1", name: "Perfil Corretor", description: "Exemplo de perfil de corretor" },
            ]
        },
        {
            category: "Gestão de Propriedades",
            icon: <Building className="h-5 w-5" />,
            routes: [
                { path: "/add-property", name: "Adicionar Propriedade", description: "Cadastrar novo imóvel" },
                { path: "/my-properties", name: "Minhas Propriedades", description: "Gerenciar seus imóveis" },
                { path: "/edit-property/1", name: "Editar Propriedade", description: "Exemplo de edição de imóvel" },
                { path: "/properties/1", name: "Detalhes da Propriedade", description: "Exemplo de detalhes de imóvel" },
                { path: "/property-proposals", name: "Propostas", description: "Gerenciar propostas de aluguel" },
            ]
        },
        {
            category: "Ferramentas e Guias",
            icon: <Calculator className="h-5 w-5" />,
            routes: [
                { path: "/tools/calculadora-aluguel", name: "Calculadora de Aluguel", description: "Calcular valores de aluguel" },
                { path: "/guides/contrato-aluguel", name: "Guia de Contrato", description: "Como fazer um contrato de aluguel" },
            ]
        },
        {
            category: "Configurações",
            icon: <FileText className="h-5 w-5" />,
            routes: [
                { path: "/user-settings", name: "Configurações", description: "Configurações da conta" },
                { path: "/user-verification", name: "Verificação", description: "Verificar conta de usuário" },
            ]
        },
        {
            category: "Páginas de Erro",
            icon: <AlertTriangle className="h-5 w-5" />,
            routes: [
                { path: "/errors/bad-request", name: "Erro 400", description: "Solicitação inválida" },
                { path: "/errors/unauthorized", name: "Erro 401", description: "Não autorizado" },
                { path: "/errors/access-denied", name: "Erro 403", description: "Acesso negado" },
                { path: "/errors/not-found", name: "Erro 404", description: "Página não encontrada" },
                { path: "/errors/too-many-request", name: "Erro 429", description: "Muitas solicitações" },
                { path: "/errors/internal-server-error", name: "Erro 500", description: "Erro interno do servidor" },
                { path: "/errors/bad-gateway", name: "Erro 502", description: "Bad Gateway" },
                { path: "/errors/service-unavailable", name: "Erro 503", description: "Serviço indisponível" },
                { path: "/errors/gateway-timeout", name: "Erro 504", description: "Gateway Timeout" },
            ]
        }
    ];

    return (
        <MainLayout>
            <div className="min-h-screen bg-[#f9f4e8] py-12">
                <div className="container mx-auto px-4">
                    <div className="text-center mb-12">
                        <h1 className="text-4xl font-heading font-bold text-[#2c3e50] mb-4">
                            Casa Alegria - Documentação
                        </h1>
                        <p className="text-lg text-muted-foreground max-w-3xl mx-auto mb-8">
                            Plataforma completa de imóveis desenvolvida como projeto acadêmico para o 5º período
                            da faculdade de Sistemas de Informação - IFPR Campus Palmas. Uma solução moderna
                            para conectar proprietários, corretores e inquilinos.
                        </p>
                        <div className="flex flex-wrap justify-center gap-3 mb-8">
                            <Badge variant="secondary" className="px-4 py-2">
                                <GraduationCap className="h-4 w-4 mr-2" />
                                Projeto Acadêmico
                            </Badge>
                            <Badge variant="secondary" className="px-4 py-2">
                                <Code className="h-4 w-4 mr-2" />
                                Full-Stack
                            </Badge>
                            <Badge variant="secondary" className="px-4 py-2">
                                <Building className="h-4 w-4 mr-2" />
                                Plataforma Imobiliária
                            </Badge>
                        </div>
                    </div>

                    {/* About */}
                    <Card className="shadow-md mb-8">
                        <CardHeader>
                            <CardTitle className="flex items-center gap-3 text-[#2c3e50]">
                                <Building className="h-6 w-6" />
                                Sobre o Casa Alegria
                            </CardTitle>
                        </CardHeader>
                        <CardContent className="space-y-4">
                            <p className="text-muted-foreground">
                                O Casa Alegria é uma plataforma desenvolvida com foco especial na dinâmica de pequenas cidades,
                                oferecendo uma API robusta e uma interface web moderna para proporcionar uma experiência fluida
                                a todos os usuários envolvidos no mercado imobiliário.
                            </p>
                            <div className="grid md:grid-cols-2 gap-6">
                                <div>
                                    <h4 className="font-semibold text-[#2c3e50] mb-2">Objetivos</h4>
                                    <ul className="text-sm text-muted-foreground space-y-1">
                                        <li>• Profissionalizar negociações imobiliárias</li>
                                        <li>• Conectar proprietários, corretores e inquilinos</li>
                                        <li>• Simplificar o processo de listagem de propriedades</li>
                                        <li>• Modernizar o mercado imobiliário em pequenas cidades</li>
                                    </ul>
                                </div>
                                <div>
                                    <h4 className="font-semibold text-[#2c3e50] mb-2">Funcionalidades</h4>
                                    <ul className="text-sm text-muted-foreground space-y-1">
                                        <li>• Gestão de usuários por perfil</li>
                                        <li>• Sistema de verificação robusto</li>
                                        <li>• Anúncios detalhados de propriedades</li>
                                        <li>• Ferramentas de comunicação</li>
                                    </ul>
                                </div>
                            </div>
                        </CardContent>
                    </Card>

                    {/* Technologics */}
                    <Card className="shadow-md mb-8">
                        <CardHeader>
                            <CardTitle className="flex items-center gap-3 text-[#2c3e50]">
                                <Code className="h-6 w-6" />
                                Stack Tecnológica
                            </CardTitle>
                            <CardDescription>
                                Tecnologias modernas utilizadas no desenvolvimento
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                                {techStack.map((tech, index) => (
                                    <div key={tech.name} className="p-3 bg-white rounded-lg border text-center">
                                        <div className="flex justify-center mb-2 text-[#e56b4e]">
                                            {tech.icon}
                                        </div>
                                        <h5 className="font-semibold text-sm text-[#2c3e50]">{tech.name}</h5>
                                        <p className="text-xs text-muted-foreground mt-1">{tech.description}</p>
                                    </div>
                                ))}
                            </div>
                        </CardContent>
                    </Card>

                    {/* Download Postman */}
                    <Card className="shadow-md mb-8">
                        <CardHeader>
                            <CardTitle className="flex items-center gap-3 text-[#2c3e50]">
                                <Download className="h-6 w-6" />
                                Recursos para Desenvolvedores
                            </CardTitle>
                            <CardDescription>
                                Ferramentas e recursos para testar e integrar com a API
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="bg-white p-6 rounded-lg border">
                                <div className="flex items-center justify-between mb-4">
                                    <div>
                                        <h4 className="font-semibold text-[#2c3e50]">Coleção Postman</h4>
                                        <p className="text-sm text-muted-foreground">
                                            Collection completa com todas as rotas da API documentadas
                                        </p>
                                    </div>
                                    <Button onClick={downloadPostmanCollection} className="bg-[#e56b4e] hover:bg-[#e56b4e]/90 text-white">
                                        <Download className="h-4 w-4 mr-2" />
                                        Baixar JSON
                                    </Button>
                                </div>
                                <div className="text-xs text-muted-foreground">
                                    <p className="mb-2">A coleção inclui:</p>
                                    <ul className="space-y-1 ml-4">
                                        <li>• Endpoints de autenticação (login, registro)</li>
                                        <li>• CRUD completo de propriedades</li>
                                        <li>• Gerenciamento de perfis de usuário</li>
                                        <li>• Variáveis de ambiente pré-configuradas</li>
                                    </ul>
                                </div>
                            </div>
                        </CardContent>
                    </Card>

                    {/* Development Team */}
                    <Card className="shadow-md mb-8">
                        <CardHeader>
                            <CardTitle className="flex items-center gap-3 text-[#2c3e50]">
                                <Users className="h-6 w-6" />
                                Equipe de Desenvolvimento
                            </CardTitle>
                            <CardDescription>
                                Estudantes do 5º período de Sistemas de Informação - IFPR Campus Palmas
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="grid md:grid-cols-2 gap-6">
                                {developers.map((dev, index) => (
                                    <div key={dev.name} className="p-4 bg-white rounded-lg border">
                                        <div className="flex items-start justify-between mb-3">
                                            <div>
                                                <h4 className="font-semibold text-[#2c3e50]">{dev.name}</h4>
                                                <p className="text-sm text-[#e56b4e] font-medium">{dev.role}</p>
                                            </div>
                                            <div className="flex gap-2">
                                                {dev.linkedin && dev.linkedin !== "#" && (
                                                    <a href={dev.linkedin} target="_blank" rel="noopener noreferrer">
                                                        <Button variant="outline" size="sm">
                                                            <Linkedin className="h-4 w-4" />
                                                        </Button>
                                                    </a>
                                                )}
                                                {dev.github && dev.github !== "#" && (
                                                    <a href={dev.github} target="_blank" rel="noopener noreferrer">
                                                        <Button variant="outline" size="sm">
                                                            <Github className="h-4 w-4" />
                                                        </Button>
                                                    </a>
                                                )}
                                            </div>
                                        </div>
                                        <p className="text-sm text-muted-foreground">{dev.description}</p>
                                    </div>
                                ))}
                            </div>
                        </CardContent>
                    </Card>

                    {/* Professors */}
                    <Card className="shadow-md mb-8">
                        <CardHeader>
                            <CardTitle className="flex items-center gap-3 text-[#2c3e50]">
                                <GraduationCap className="h-6 w-6" />
                                Professores Orientadores
                            </CardTitle>
                            <CardDescription>
                                Docentes responsáveis pelas disciplinas envolvidas no projeto
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="grid md:grid-cols-2 gap-6">
                                {professors.map((prof, index) => (
                                    <div key={prof.name} className="p-4 bg-white rounded-lg border">
                                        <div className="flex items-start justify-between mb-3">
                                            <div>
                                                <h4 className="font-semibold text-[#2c3e50]">{prof.name}</h4>
                                                <p className="text-sm text-[#e56b4e] font-medium">{prof.subject}</p>
                                            </div>
                                            <a href={prof.linkedin} target="_blank" rel="noopener noreferrer">
                                                <Button variant="outline" size="sm">
                                                    <Linkedin className="h-4 w-4" />
                                                </Button>
                                            </a>
                                        </div>
                                        <p className="text-sm text-muted-foreground">{prof.description}</p>
                                    </div>
                                ))}
                            </div>
                        </CardContent>
                    </Card>

                    <Separator className="my-8" />

                    {/* Routes of Application */}
                    <div className="mb-8">
                        <h2 className="text-2xl font-heading font-bold text-[#2c3e50] mb-4">
                            Mapeamento de Rotas
                        </h2>
                        <p className="text-muted-foreground mb-6">
                            Navegue por todas as páginas e funcionalidades disponíveis na plataforma
                        </p>
                    </div>

                    <div className="grid gap-8">
                        {routes.map((category, index) => (
                            <Card key={category.category} className="shadow-md">
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-3 text-[#2c3e50]">
                                        {category.icon}
                                        {category.category}
                                    </CardTitle>
                                    <CardDescription>
                                        {category.routes.length} páginas disponíveis nesta categoria
                                    </CardDescription>
                                </CardHeader>
                                <CardContent>
                                    <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                                        {category.routes.map((route, routeIndex) => (
                                            <div key={route.name} className="p-4 bg-white rounded-lg border border-gray-200 hover:shadow-md transition-shadow">
                                                <div className="flex flex-col gap-2">
                                                    <div className="flex items-center justify-between">
                                                        <h3 className="font-semibold text-[#2c3e50]">{route.name}</h3>
                                                        <Badge variant="outline" className="text-xs">
                                                            {route.path}
                                                        </Badge>
                                                    </div>
                                                    <p className="text-sm text-muted-foreground mb-3">
                                                        {route.description}
                                                    </p>
                                                    <Button
                                                        asChild
                                                        variant="outline"
                                                        size="sm"
                                                        className="self-start hover:bg-[#e56b4e] hover:text-white hover:border-[#e56b4e]"
                                                    >
                                                        <Link href={route.path}>
                                                            Visitar página
                                                        </Link>
                                                    </Button>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </CardContent>
                            </Card>
                        ))}
                    </div>

                    <div className="mt-12 text-center">
                        <Card className="bg-white shadow-md max-w-3xl mx-auto">
                            <CardHeader>
                                <CardTitle className="text-[#2c3e50]">Sobre o IFPR Campus Palmas</CardTitle>
                            </CardHeader>
                            <CardContent className="text-left space-y-4">
                                <p className="text-muted-foreground">
                                    Este projeto foi desenvolvido como parte do currículo acadêmico do Instituto Federal do Paraná (IFPR) Campus Palmas,
                                    no curso de Sistemas de Informação, demonstrando a aplicação prática de conceitos de engenharia de software,
                                    desenvolvimento web e gestão de projetos.
                                </p>
                                <div className="grid md:grid-cols-2 gap-4 mt-4">
                                    <div>
                                        <h4 className="font-semibold text-[#2c3e50] mb-2">Disciplinas Envolvidas:</h4>
                                        <ul className="text-sm text-muted-foreground space-y-1">
                                            <li>• SIS05502 - Programação de Software</li>
                                            <li>• SIS05501 - Análise e Projeto de Sistemas</li>
                                        </ul>
                                    </div>
                                    <div>
                                        <h4 className="font-semibold text-[#2c3e50] mb-2">Competências Desenvolvidas:</h4>
                                        <ul className="text-sm text-muted-foreground space-y-1">
                                            <li>• Desenvolvimento Full-Stack</li>
                                            <li>• Modelagem de Sistemas</li>
                                            <li>• Arquitetura de Software</li>
                                            <li>• Gestão de Projetos</li>
                                        </ul>
                                    </div>
                                </div>
                            </CardContent>
                        </Card>
                    </div>
                    <Separator className="my-8" />

                    <div className="mt-12 text-center">
                        <h2 className="text-2xl font-heading font-bold text-[#2c3e50] mb-4">
                            Documento de Software Detalhada em PDF
                        </h2>
                        <p className="text-muted-foreground mb-6">
                            Confira a versão completa da documentação do projeto.
                        </p>
                        <p className="text-muted-foreground mb-6">
                            que serve como um artefato crucial para a disciplina SIS05501 - Análise e Projeto de Sistemas.
                        </p>
                        <Card className="bg-white shadow-md max-w-5xl mx-auto p-4">
                            <CardContent>
                                <iframe
                                    src="/Documento Software KitNet - Analise e Projeto de Sistemas.pdf" // Caminho para o seu PDF na pasta public
                                    width="100%"
                                    height="800px" // Ajuste a altura conforme necessário
                                    style={{ border: 'none' }}
                                    title="Documentação do Projeto Casa Alegria"
                                >
                                    Seu navegador não suporta a incorporação de PDFs. Você pode <a href="/documentacao.pdf" target="_blank" rel="noopener noreferrer">baixar o PDF aqui</a>.
                                </iframe>
                            </CardContent>
                        </Card>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default DocumentationContent;