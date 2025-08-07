"use client";

import React from "react";
import { Button } from "@/components/ui/button";
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import MainLayout from "@/components/layout/MainLayout";
import {
    Users,
    Building,
    Download,
    Github,
    Linkedin,
    GraduationCap,
    Code,
    XCircle,
    CheckCircle,
    Video,
} from "lucide-react";
import Link from "next/link";
import postmanCollection from "@/data/kitnet.postman_collectionV21.json";
import { techStack } from "@/mock/documentationsData/techStack";
import { developers } from "@/mock/documentationsData/developers";
import { professors } from "@/mock/documentationsData/professors";
import { routes } from "@/mock/documentationsData/routes";

const DocumentationContent = () => {
    const downloadPostmanCollection = () => {
        const dataStr = JSON.stringify(postmanCollection, null, 2);
        const dataUri = `data:application/json;charset=utf-8,${encodeURIComponent(dataStr)}`;

        const exportFileDefaultName = "Casa_Alegria_API_Collection.json";

        const linkElement = document.createElement("a");
        linkElement.setAttribute("href", dataUri);
        linkElement.setAttribute("download", exportFileDefaultName);
        linkElement.click();
    };


    return (
        <MainLayout>
            <div className="min-h-screen bg-[#f9f4e8] py-12">
                <div className="container mx-auto px-4">
                    <div className="text-center mb-12">
                        <h1 className="text-4xl font-heading font-bold text-[#2c3e50] mb-4">
                            KitNet - Documentação
                        </h1>
                        <p className="text-lg text-muted-foreground max-w-3xl mx-auto mb-8">
                            Plataforma completa de imóveis desenvolvida como projeto acadêmico
                            para o 5º período da faculdade de Sistemas de Informação - IFPR
                            Campus Palmas. Uma solução moderna para conectar proprietários,
                            corretores e inquilinos.
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
                                Sobre o KitNet
                            </CardTitle>
                        </CardHeader>
                        <CardContent className="space-y-4">
                            <p className="text-muted-foreground">
                                O KitNet é uma plataforma desenvolvida com foco especial
                                na dinâmica de pequenas cidades, oferecendo uma API robusta e
                                uma interface web moderna para proporcionar uma experiência
                                fluida a todos os usuários envolvidos no mercado imobiliário.
                            </p>
                            <div className="grid md:grid-cols-2 gap-6">
                                <div>
                                    <h4 className="font-semibold text-[#2c3e50] mb-2">
                                        Objetivos
                                    </h4>
                                    <ul className="text-sm text-muted-foreground space-y-1">
                                        <li>• Profissionalizar negociações imobiliárias</li>
                                        <li>• Conectar proprietários, corretores e inquilinos</li>
                                        <li>
                                            • Simplificar o processo de listagem de propriedades
                                        </li>
                                        <li>
                                            • Modernizar o mercado imobiliário em pequenas cidades
                                        </li>
                                    </ul>
                                </div>
                                <div>
                                    <h4 className="font-semibold text-[#2c3e50] mb-2">
                                        Funcionalidades
                                    </h4>
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
                                    <div
                                        key={tech.name}
                                        className="p-3 bg-white rounded-lg border text-center"
                                    >
                                        <div className="flex justify-center mb-2 text-[#e56b4e]">
                                            {tech.icon}
                                        </div>
                                        <h5 className="font-semibold text-sm text-[#2c3e50]">
                                            {tech.name}
                                        </h5>
                                        <p className="text-xs text-muted-foreground mt-1">
                                            {tech.description}
                                        </p>
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
                                        <h4 className="font-semibold text-[#2c3e50]">
                                            Coleção Postman
                                        </h4>
                                        <p className="text-sm text-muted-foreground">
                                            Collection completa com todas as rotas da API documentadas
                                        </p>
                                    </div>
                                    <Button
                                        onClick={downloadPostmanCollection}
                                        className="bg-[#e56b4e] hover:bg-[#e56b4e]/90 text-white"
                                    >
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
                                Estudantes do 5º período de Sistemas de Informação - IFPR Campus
                                Palmas
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="grid md:grid-cols-2 gap-6">
                                {developers.map((dev, index) => (
                                    <div
                                        key={dev.name}
                                        className="p-4 bg-white rounded-lg border"
                                    >
                                        <div className="flex items-start justify-between mb-3">
                                            <div>
                                                <h4 className="font-semibold text-[#2c3e50]">
                                                    {dev.name}
                                                </h4>
                                                <p className="text-sm text-[#e56b4e] font-medium">
                                                    {dev.role}
                                                </p>
                                            </div>
                                            <div className="flex gap-2">
                                                {dev.linkedin && dev.linkedin !== "#" && (
                                                    <a
                                                        href={dev.linkedin}
                                                        target="_blank"
                                                        rel="noopener noreferrer"
                                                    >
                                                        <Button variant="outline" size="sm">
                                                            <Linkedin className="h-4 w-4" />
                                                        </Button>
                                                    </a>
                                                )}
                                                {dev.github && dev.github !== "#" && (
                                                    <a
                                                        href={dev.github}
                                                        target="_blank"
                                                        rel="noopener noreferrer"
                                                    >
                                                        <Button variant="outline" size="sm">
                                                            <Github className="h-4 w-4" />
                                                        </Button>
                                                    </a>
                                                )}
                                            </div>
                                        </div>
                                        <p className="text-sm text-muted-foreground">
                                            {dev.description}
                                        </p>
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
                                    <div
                                        key={prof.name}
                                        className="p-4 bg-white rounded-lg border"
                                    >
                                        <div className="flex items-start justify-between mb-3">
                                            <div>
                                                <h4 className="font-semibold text-[#2c3e50]">
                                                    {prof.name}
                                                </h4>
                                                <p className="text-sm text-[#e56b4e] font-medium">
                                                    {prof.subject}
                                                </p>
                                            </div>
                                            <a
                                                href={prof.linkedin}
                                                target="_blank"
                                                rel="noopener noreferrer"
                                            >
                                                <Button variant="outline" size="sm">
                                                    <Linkedin className="h-4 w-4" />
                                                </Button>
                                            </a>
                                        </div>
                                        <p className="text-sm text-muted-foreground">
                                            {prof.description}
                                        </p>
                                    </div>
                                ))}
                            </div>
                        </CardContent>
                    </Card>

                    <Separator className="my-8" />

                    <Card className="mb-12">
                        <CardHeader>
                            <div className="flex items-center gap-2 mb-2">
                                <Video className="h-6 w-6 text-[#e56b4e]" />
                                <CardTitle className="text-2xl">Vídeo Explicativo</CardTitle>
                            </div>
                            <CardDescription>
                                Uma descricao bem feita
                            </CardDescription>
                        </CardHeader>
                        <CardContent>
                            <div className="aspect-video w-full rounded-lg overflow-hidden bg-muted">
                                <iframe
                                    width="100%"
                                    height="100%"
                                    src="https://www.youtube.com/embed/dQw4w9WgXcQ"
                                    title="Mostrando o projeto KitNet"
                                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                                    allowFullScreen
                                    className="w-full h-full"
                                />
                            </div>
                            <p className="text-sm text-muted-foreground mt-4">
                                <strong>Nota:</strong> teste.
                            </p>
                        </CardContent>
                    </Card>

                    <Separator className="my-8" />

                    {/* Routes of Application */}
                    <div className="mb-8">
                        <h2 className="text-2xl font-heading font-bold text-[#2c3e50] mb-4">
                            Mapeamento de Rotas
                        </h2>
                        <p className="text-muted-foreground mb-6">
                            Navegue por todas as páginas e funcionalidades disponíveis na
                            plataforma
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
                                            <div
                                                key={route.name}
                                                className="p-4 bg-white rounded-lg border border-gray-200 hover:shadow-md transition-shadow"
                                            >
                                                <div className="flex flex-col gap-2">
                                                    <div className="flex items-center justify-between">
                                                        <h3 className="font-semibold text-[#2c3e50]">
                                                            {route.name}
                                                        </h3>
                                                        <div className="flex flex-col items-center gap-2">
                                                            <Badge variant="outline" className="text-xs">
                                                                {route.path}
                                                            </Badge>
                                                            <Badge
                                                                variant="outline"
                                                                style={
                                                                    route.implemented
                                                                        ? { backgroundColor: "#77c84188" }
                                                                        : {
                                                                            backgroundColor: "#c84141",
                                                                            color: "white",
                                                                        }
                                                                }
                                                                className="text-xs flex flex-row justify-center items-center gap-2"
                                                            >
                                                                {route.implemented
                                                                    ? "Implementada"
                                                                    : "Não Implementada"}
                                                                {route.implemented ? (
                                                                    <CheckCircle />
                                                                ) : (
                                                                    <XCircle />
                                                                )}
                                                            </Badge>
                                                        </div>
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
                                                        <Link href={route.path}>Visitar página</Link>
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
                                <CardTitle className="text-[#2c3e50]">
                                    Sobre o IFPR Campus Palmas
                                </CardTitle>
                            </CardHeader>
                            <CardContent className="text-left space-y-4">
                                <p className="text-muted-foreground">
                                    Este projeto foi desenvolvido como parte do currículo
                                    acadêmico do Instituto Federal do Paraná (IFPR) Campus Palmas,
                                    no curso de Sistemas de Informação, demonstrando a aplicação
                                    prática de conceitos de engenharia de software,
                                    desenvolvimento web e gestão de projetos.
                                </p>
                                <div className="grid md:grid-cols-2 gap-4 mt-4">
                                    <div>
                                        <h4 className="font-semibold text-[#2c3e50] mb-2">
                                            Disciplinas Envolvidas:
                                        </h4>
                                        <ul className="text-sm text-muted-foreground space-y-1">
                                            <li>• SIS05502 - Programação de Software</li>
                                            <li>• SIS05501 - Análise e Projeto de Sistemas</li>
                                        </ul>
                                    </div>
                                    <div>
                                        <h4 className="font-semibold text-[#2c3e50] mb-2">
                                            Competências Desenvolvidas:
                                        </h4>
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
                            que serve como um artefato crucial para a disciplina SIS05501 -
                            Análise e Projeto de Sistemas.
                        </p>
                        <Card className="bg-white shadow-md max-w-5xl mx-auto p-4">
                            <CardContent>
                                <iframe
                                    src="/Documento Software KitNet - Analise e Projeto de Sistemas.pdf" // Caminho para o seu PDF na pasta public
                                    width="100%"
                                    height="800px" // Ajuste a altura conforme necessário
                                    style={{ border: "none" }}
                                    title="Documentação do Projeto KitNet"
                                >
                                    Seu navegador não suporta a incorporação de PDFs. Você pode{" "}
                                    <a
                                        href="/documentacao.pdf"
                                        target="_blank"
                                        rel="noopener noreferrer"
                                    >
                                        baixar o PDF aqui
                                    </a>
                                    .
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
