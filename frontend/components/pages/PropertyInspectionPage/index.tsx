import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, Search, Camera, CheckCircle } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const PropertyInspectionPage = () => {
    return (
        <MainLayout>
            <div className="py-16 bg-gradient-to-b from-[#f9f4e8] to-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="mb-8">
                        <Link href="/guides" className="inline-flex items-center text-[#e56b4e] hover:underline mb-4">
                            <ArrowLeft className="mr-2 h-4 w-4" />
                            Voltar aos Guias
                        </Link>
                        <h1 className="text-4xl font-heading font-bold mb-4">Como Fazer Vistoria</h1>
                        <p className="text-xl text-muted-foreground">
                            Guia completo para vistoria de entrada e saída
                        </p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 space-y-8">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Search className="h-5 w-5 text-[#e56b4e]" />
                                        Vistoria de Entrada
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <p className="text-muted-foreground mb-4">
                                        A vistoria de entrada é fundamental para registrar o estado do imóvel antes da ocupação.
                                    </p>

                                    <h3 className="font-semibold">O que verificar:</h3>
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <div>
                                            <h4 className="font-medium text-[#e56b4e]">Estrutura</h4>
                                            <ul className="text-sm text-muted-foreground space-y-1">
                                                <li>• Paredes (rachaduras, umidade)</li>
                                                <li>• Teto (vazamentos, manchas)</li>
                                                <li>• Piso (riscos, quebras)</li>
                                                <li>• Portas e janelas</li>
                                            </ul>
                                        </div>
                                        <div>
                                            <h4 className="font-medium text-[#e56b4e]">Instalações</h4>
                                            <ul className="text-sm text-muted-foreground space-y-1">
                                                <li>• Torneiras e registros</li>
                                                <li>• Chuveiros e aquecedor</li>
                                                <li>• Tomadas e interruptores</li>
                                                <li>• Lâmpadas e luminárias</li>
                                            </ul>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Camera className="h-5 w-5 text-[#e56b4e]" />
                                        Documentação da Vistoria
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <h3 className="font-semibold">Como documentar:</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Tire fotos de todos os cômodos</li>
                                        <li>• Fotografe defeitos e problemas</li>
                                        <li>• Anote tudo no laudo de vistoria</li>
                                        <li>• Faça ambas as partes assinarem</li>
                                        <li>• Guarde uma cópia do laudo</li>
                                    </ul>

                                    <div className="p-4 bg-amber-50 border border-amber-200 rounded-lg">
                                        <p className="text-sm font-medium text-amber-800">⚠️ Importante</p>
                                        <p className="text-sm text-amber-700">
                                            Problemas não registrados na vistoria podem ser cobrados na saída
                                        </p>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Checklist de Vistoria</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                        <div>
                                            <h4 className="font-semibold mb-3">Cozinha</h4>
                                            <ul className="space-y-1 text-sm text-muted-foreground">
                                                <li>□ Fogão e forno</li>
                                                <li>□ Pia e torneira</li>
                                                <li>□ Armários e gavetas</li>
                                                <li>□ Azulejos</li>
                                                <li>□ Tomadas</li>
                                            </ul>
                                        </div>
                                        <div>
                                            <h4 className="font-semibold mb-3">Banheiro</h4>
                                            <ul className="space-y-1 text-sm text-muted-foreground">
                                                <li>□ Vaso sanitário</li>
                                                <li>□ Pia e torneira</li>
                                                <li>□ Chuveiro e registro</li>
                                                <li>□ Azulejos e rejunte</li>
                                                <li>□ Espelho</li>
                                            </ul>
                                        </div>
                                        <div>
                                            <h4 className="font-semibold mb-3">Quartos</h4>
                                            <ul className="space-y-1 text-sm text-muted-foreground">
                                                <li>□ Armários embutidos</li>
                                                <li>□ Tomadas e interruptores</li>
                                                <li>□ Janelas e fechaduras</li>
                                                <li>□ Pisos</li>
                                                <li>□ Pintura</li>
                                            </ul>
                                        </div>
                                        <div>
                                            <h4 className="font-semibold mb-3">Sala</h4>
                                            <ul className="space-y-1 text-sm text-muted-foreground">
                                                <li>□ Tomadas e TV</li>
                                                <li>□ Janelas</li>
                                                <li>□ Piso</li>
                                                <li>□ Pintura</li>
                                                <li>□ Iluminação</li>
                                            </ul>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <CheckCircle className="h-5 w-5 text-[#e56b4e]" />
                                        Vistoria de Saída
                                    </CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="mb-4">
                                        A vistoria de saída compara o estado atual com o laudo de entrada.
                                    </p>
                                    <h3 className="font-semibold mb-3">Responsabilidades do inquilino:</h3>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Entregar o imóvel limpo</li>
                                        <li>• Reparar danos causados pelo uso</li>
                                        <li>• Repor itens quebrados ou perdidos</li>
                                        <li>• Pintura (se especificado no contrato)</li>
                                    </ul>

                                    <div className="p-4 bg-green-50 border border-green-200 rounded-lg mt-4">
                                        <p className="text-sm font-medium text-green-800">💡 Dica</p>
                                        <p className="text-sm text-green-700">
                                            Desgaste natural não pode ser cobrado do inquilino
                                        </p>
                                    </div>
                                </CardContent>
                            </Card>
                        </div>

                        <div className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Modelos de Laudo</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <Button variant="outline" className="w-full justify-start">
                                        📄 Download Laudo de Vistoria
                                    </Button>
                                    <Button variant="outline" className="w-full justify-start">
                                        📋 Checklist Imprimível
                                    </Button>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Guias Relacionados</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <Link href="/guides/como-alugar" className="block text-sm text-[#e56b4e]hover:underline">
                                        → Como alugar um imóvel
                                    </Link>
                                    <Link href="/guides/contrato-aluguel" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Contratos de aluguel
                                    </Link>
                                    <Link href="/guides/direitos-inquilino" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Direitos do inquilino
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Precisa de Ajuda?</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        Oferecemos serviço de vistoria profissional
                                    </p>
                                    <Button className="w-full">
                                        Solicitar Vistoria
                                    </Button>
                                </CardContent>
                            </Card>
                        </div>
                    </div>
                </div>
            </div>
        </MainLayout>
    );
};

export default PropertyInspectionPage;