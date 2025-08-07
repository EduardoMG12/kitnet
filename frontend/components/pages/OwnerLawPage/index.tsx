import React from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { ArrowLeft, Home, CheckCircle } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const OwnerLawPage = () => {
    return (
        <MainLayout>
            <div className="py-16 bg-gradient-to-b from-[#f9f4e8] to-white">
                <div className="px-4 mx-auto max-w-7xl">
                    <div className="mb-8">
                        <Link href="/guides" className="inline-flex items-center text-[#e56b4e] hover:underline mb-4">
                            <ArrowLeft className="mr-2 h-4 w-4" />
                            Voltar aos Guias
                        </Link>
                        <h1 className="text-4xl font-heading font-bold mb-4">Direitos do Proprietário</h1>
                        <p className="text-xl text-muted-foreground">
                            Conheça seus direitos como proprietário locador
                        </p>
                    </div>

                    <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                        <div className="lg:col-span-2 space-y-8">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="flex items-center gap-2">
                                        <Home className="h-5 w-5 text-[#e56b4e]" />
                                        Principais Direitos
                                    </CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-4">
                                    <div className="space-y-3">
                                        <div className="flex items-start gap-3">
                                            <CheckCircle className="h-5 w-5 text-green-600 mt-0.5 flex-shrink-0" />
                                            <div>
                                                <h3 className="font-semibold">Recebimento pontual do aluguel</h3>
                                                <p className="text-muted-foreground">Direito ao pagamento em dia conforme contrato</p>
                                            </div>
                                        </div>
                                        <div className="flex items-start gap-3">
                                            <CheckCircle className="h-5 w-5 text-green-600 mt-0.5 flex-shrink-0" />
                                            <div>
                                                <h3 className="font-semibold">Reajuste anual</h3>
                                                <p className="text-muted-foreground">Direito de reajustar o aluguel por índice oficial</p>
                                            </div>
                                        </div>
                                        <div className="flex items-start gap-3">
                                            <CheckCircle className="h-5 w-5 text-green-600 mt-0.5 flex-shrink-0" />
                                            <div>
                                                <h3 className="font-semibold">Retomada do imóvel</h3>
                                                <p className="text-muted-foreground">Direito de retomar para uso próprio ou familiar</p>
                                            </div>
                                        </div>
                                        <div className="flex items-start gap-3">
                                            <CheckCircle className="h-5 w-5 text-green-600 mt-0.5 flex-shrink-0" />
                                            <div>
                                                <h3 className="font-semibold">Fiscalização do imóvel</h3>
                                                <p className="text-muted-foreground">Direito de vistoriar mediante aviso prévio</p>
                                            </div>
                                        </div>
                                    </div>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Garantias de pagamento</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="mb-4">
                                        O proprietário pode exigir garantias para assegurar o cumprimento das obrigações.
                                    </p>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Depósito caução (até 3 aluguéis)</li>
                                        <li>• Fiador com renda comprovada</li>
                                        <li>• Seguro fiança</li>
                                        <li>• Título de capitalização</li>
                                    </ul>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle>Ação de despejo</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="mb-4">
                                        Em caso de descumprimento do contrato, o proprietário pode entrar com ação de despejo.
                                    </p>
                                    <ul className="space-y-2 text-muted-foreground">
                                        <li>• Falta de pagamento (após 3 dias de atraso)</li>
                                        <li>• Infração contratual ou legal</li>
                                        <li>• Falta de caução ou fiador</li>
                                        <li>• Uso inadequado do imóvel</li>
                                    </ul>
                                </CardContent>
                            </Card>
                        </div>

                        <div className="space-y-6">
                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Documentos Úteis</CardTitle>
                                </CardHeader>
                                <CardContent className="space-y-3">
                                    <Link href="/guides/modelo-contrato" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Modelo de contrato
                                    </Link>
                                    <Link href="/guides/lei-inquilinato" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Lei do Inquilinato completa
                                    </Link>
                                    <Link href="/tools/quanto-cobrar" className="block text-sm text-[#e56b4e] hover:underline">
                                        → Quanto cobrar de aluguel
                                    </Link>
                                </CardContent>
                            </Card>

                            <Card>
                                <CardHeader>
                                    <CardTitle className="text-lg">Precisa de Ajuda?</CardTitle>
                                </CardHeader>
                                <CardContent>
                                    <p className="text-sm text-muted-foreground mb-4">
                                        Entre em contato com nossos especialistas
                                    </p>
                                    <Button className="w-full">
                                        Falar com Especialista
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

export default OwnerLawPage;