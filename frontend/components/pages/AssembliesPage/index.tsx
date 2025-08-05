'use client';
import React from "react";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, Users, Calendar, FileText, ListChecks } from "lucide-react"; // Adicionado FileText e ListChecks
import MainLayout from "@/components/layout/MainLayout";
import { Breadcrumb, BreadcrumbItem, BreadcrumbLink, BreadcrumbList, BreadcrumbPage, BreadcrumbSeparator } from "@/components/ui/breadcrumb";
import Link from "next/link";

const AssembliesPage = () => {
    // Dados de exemplo para o calendário de assembleias
    const upcomingAssemblies = [
        { id: 0, date: "15/08/2025", time: "19:00", type: "Ordinária", agenda: "Aprovação de contas e orçamento" },
        { id: 1, date: "22/09/2025", time: "18:30", type: "Extraordinária", agenda: "Discussão sobre reforma da fachada" },
        { id: 2, date: "10/11/2025", time: "20:00", type: "Ordinária", agenda: "Eleição de síndico e conselho" },
    ];

    return (
        <MainLayout>
            <div className="py-8 bg-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <Breadcrumb className="mb-6">
                        <BreadcrumbList>
                            <BreadcrumbItem>
                                <BreadcrumbLink asChild>
                                    <Link href="/guides">Guias</Link>
                                </BreadcrumbLink>
                            </BreadcrumbItem>
                            <BreadcrumbSeparator />
                            <BreadcrumbPage>Assembleias</BreadcrumbPage>
                        </BreadcrumbList>
                    </Breadcrumb>

                    <div className="max-w-4xl mx-auto">
                        <header className="mb-8">
                            <h1 className="text-3xl md:text-4xl font-heading font-bold mb-4">
                                Assembleias Condominiais
                            </h1>
                            <p className="text-lg text-muted-foreground">
                                Entenda como funcionam as assembleias e seus direitos de participação
                            </p>
                        </header>

                        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                            <div className="lg:col-span-2 space-y-8">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="flex items-center gap-2">
                                            <Users className="h-5 w-5 text-blue-600" />
                                            Tipos de Assembleia
                                        </CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Assembleia Ordinária</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Realizada anualmente</li>
                                                <li>• Aprovação do orçamento</li>
                                                <li>• Prestação de contas</li>
                                                <li>• Eleição do síndico e conselho</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Assembleia Extraordinária</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Quando necessário</li>
                                                <li>• Assuntos urgentes</li>
                                                <li>• Reformas e obras</li>
                                                <li>• Alteração da convenção</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Como Funciona</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div>
                                            <h3 className="font-medium mb-2">Convocação</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Edital com 10 dias de antecedência</li>
                                                <li>• Afixado em local visível</li>
                                                <li>• Ordem do dia especificada</li>
                                                <li>• Primeira e segunda convocação</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Quórum de Instalação</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Primeira convocação: 2/3 das frações</li>
                                                <li>• Segunda convocação: 1/3 das frações</li>
                                                <li>• Terceira convocação: qualquer número</li>
                                            </ul>
                                        </div>

                                        <div>
                                            <h3 className="font-medium mb-2">Quórum de Aprovação</h3>
                                            <ul className="text-sm text-muted-foreground space-y-1 ml-4">
                                                <li>• Decisões simples: maioria presente</li>
                                                <li>• Alteração da convenção: 2/3 do total</li>
                                                <li>• Obras: maioria das frações</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle>Seus Direitos</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-4">
                                        <div className="bg-green-50 p-4 rounded-lg border-l-4 border-green-400">
                                            <h4 className="font-medium text-green-800 mb-2">✅ Como Condômino</h4>
                                            <ul className="text-sm text-green-700 space-y-1">
                                                <li>• Participar e votar nas assembleias</li>
                                                <li>• Ser eleito síndico ou conselheiro</li>
                                                <li>• Solicitar convocação extraordinária</li>
                                                <li>• Contestar decisões irregulares</li>
                                            </ul>
                                        </div>
                                    </CardContent>
                                </Card>
                            </div>

                            <div className="space-y-6">
                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg flex items-center gap-2">
                                            <Calendar className="h-4 w-4" />
                                            Próximas Assembleias
                                        </CardTitle>
                                        <p>Exemplos:</p>
                                    </CardHeader>
                                    <CardContent>
                                        {upcomingAssemblies.length > 0 ? (
                                            <ul className="space-y-3">
                                                {upcomingAssemblies.map((assembly) => (

                                                    <li key={assembly.id} className="p-3 bg-muted rounded-lg border border-muted-foreground/10">
                                                        <p className="font-medium text-sm text-[#e56b4e]">{assembly.type}</p>
                                                        <p className="text-xs text-muted-foreground">
                                                            Data: {assembly.date} às {assembly.time}
                                                        </p>
                                                        <p className="text-xs text-muted-foreground">
                                                            Pauta: {assembly.agenda}
                                                        </p>
                                                    </li>
                                                ))}
                                            </ul>
                                        ) : (
                                            <p className="text-sm text-muted-foreground">
                                                Nenhuma assembleia agendada no momento.
                                            </p>
                                        )}
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Documentos Essenciais</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        {/* Os botões abaixo não abrem os documentos diretamente no preview.
                                            Você precisaria implementar a lógica para abrir um modal, uma nova página
                                            ou iniciar um download com o conteúdo fornecido nos documentos abaixo.
                                        */}
                                        <Button asChild variant="outline" className="w-full justify-start text-sm">
                                            <a href="/documentos/edital_exemplo.md" download="modelo_edital_convocacao_exemplo.md">
                                                <FileText className="mr-2 h-4 w-4" />
                                                Modelo Edital
                                            </a>
                                        </Button>
                                        <Button asChild variant="outline" className="w-full justify-start text-sm">
                                            <a href="/documentos/ata_exemplo.md" download="modelo_ata_assembleia_exemplo.md">
                                                <FileText className="mr-2 h-4 w-4" />
                                                Modelo Ata
                                            </a>
                                        </Button>
                                        <Button asChild variant="outline" className="w-full justify-start text-sm">
                                            <a href="/documentos/lista_presenca_exemplo.md" download="modelo_lista_presenca_exemplo.md">
                                                <ListChecks className="mr-2 h-4 w-4" />
                                                Modelo de Lista de Presença
                                            </a>
                                        </Button>
                                    </CardContent>
                                </Card>

                                <Card>
                                    <CardHeader>
                                        <CardTitle className="text-lg">Artigos Relacionados</CardTitle>
                                    </CardHeader>
                                    <CardContent className="space-y-3">
                                        <Link href="/guides/regras-condominio" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Regras de Condomínio
                                        </Link>
                                        <Link href="/guides/taxas-condominio" className="block text-sm text-[#e56b4e] hover:underline">
                                            → Taxas de Condomínio
                                        </Link>
                                    </CardContent>
                                </Card>
                            </div>
                        </div>

                        <div className="mt-8">
                            <Link href="/guides">
                                <Button variant="outline">
                                    <ArrowLeft className="mr-2 h-4 w-4" />
                                    Voltar aos Guias
                                </Button>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default AssembliesPage;