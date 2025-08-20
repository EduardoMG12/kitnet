'use client';

import React, { useState, useEffect } from "react";

import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { CheckCircle, XCircle, Loader2, Mail, ArrowRight } from "lucide-react";
import { redirect, RedirectType, useSearchParams } from "next/navigation";
import Link from "next/link";

const EmailConfirmationPage = () => {
    const searchParams = useSearchParams();

    const token = searchParams.get("token");
    const email = searchParams.get("email");

    const [status, setStatus] = useState<"loading" | "success" | "error" | "expired">("loading");
    const [isRedirecting, setIsRedirecting] = useState(false);

    useEffect(() => {
        const confirmEmail = async () => {
            if (!token) {
                setStatus("error");
                return;
            }

            // Simulate API call to confirm email
            setTimeout(() => {
                // Simulate different scenarios based on token
                if (token === "expired") {
                    setStatus("expired");
                } else if (token === "invalid") {
                    setStatus("error");
                } else {
                    setStatus("success");
                }
            }, 2000);
        };

        confirmEmail();
    }, [token]);

    const handleContinue = () => {
        setIsRedirecting(true);
        setTimeout(() => {
            redirect("/login", RedirectType.push);
        }, 1000);
    };

    const resendConfirmation = async () => {
        // Simulate resending confirmation email
        // In real app, this would call your API
        alert("Email de confirmação reenviado!");
    };

    if (status === "loading") {
        return (
            <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-terracotta/5 to-sage/5">
                <Card className="w-full max-w-md">
                    <CardHeader className="text-center">
                        <div className="mx-auto w-12 h-12 bg-blue-100 rounded-full flex items-center justify-center mb-4">
                            <Loader2 className="h-6 w-6 text-blue-600 animate-spin" />
                        </div>
                        <CardTitle className="text-2xl font-bold">Confirmando Email</CardTitle>
                        <CardDescription>
                            Aguarde enquanto confirmamos seu endereço de email
                        </CardDescription>
                    </CardHeader>
                    <CardContent>
                        <div className="text-center">
                            <p className="text-sm text-muted-foreground">
                                Isso pode levar alguns segundos...
                            </p>
                        </div>
                    </CardContent>
                </Card>
            </div>
        );
    }

    if (status === "success") {
        return (
            <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-terracotta/5 to-sage/5">
                <Card className="w-full max-w-md">
                    <CardHeader className="text-center">
                        <div className="mx-auto w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mb-4">
                            <CheckCircle className="h-6 w-6 text-green-600" />
                        </div>
                        <CardTitle className="text-2xl font-bold">Email Confirmado!</CardTitle>
                        <CardDescription>
                            Sua conta foi ativada com sucesso
                        </CardDescription>
                    </CardHeader>
                    <CardContent className="space-y-4">
                        {email && (
                            <div className="text-center p-3 bg-green-50 rounded-lg border border-green-200">
                                <p className="text-sm text-green-800">
                                    <strong>Email confirmado:</strong>
                                </p>
                                <p className="text-sm font-medium text-green-900">{email}</p>
                            </div>
                        )}

                        <Alert>
                            <CheckCircle className="h-4 w-4" />
                            <AlertDescription>
                                <strong>Parabéns!</strong> Sua conta está agora totalmente ativa.
                                Você pode fazer login e começar a usar todos os recursos da plataforma.
                            </AlertDescription>
                        </Alert>

                        <div className="space-y-3 pt-4">
                            <Button
                                onClick={handleContinue}
                                className="w-full bg-terracotta hover:bg-terracotta/90"
                                disabled={isRedirecting}
                            >
                                {isRedirecting ? (
                                    <>
                                        <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                                        Redirecionando...
                                    </>
                                ) : (
                                    <>
                                        Fazer Login
                                        <ArrowRight className="ml-2 h-4 w-4" />
                                    </>
                                )}
                            </Button>

                            <Link href="/register">
                                <Button variant="outline" className="w-full">
                                    Criar outra conta
                                </Button>
                            </Link>
                        </div>

                        <div className="pt-4 border-t">
                            <h3 className="font-semibold text-sm mb-2">Próximos passos:</h3>
                            <ul className="text-sm text-muted-foreground space-y-1">
                                <li>• Complete seu perfil</li>
                                <li>• Explore imóveis disponíveis</li>
                                <li>• Configure suas preferências</li>
                                <li>• Entre em contato com proprietários</li>
                            </ul>
                        </div>
                    </CardContent>
                </Card>
            </div>
        );
    }

    if (status === "expired") {
        return (
            <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-terracotta/5 to-sage/5">
                <Card className="w-full max-w-md">
                    <CardHeader className="text-center">
                        <div className="mx-auto w-12 h-12 bg-yellow-100 rounded-full flex items-center justify-center mb-4">
                            <XCircle className="h-6 w-6 text-yellow-600" />
                        </div>
                        <CardTitle className="text-2xl font-bold">Link Expirado</CardTitle>
                        <CardDescription>
                            Este link de confirmação expirou
                        </CardDescription>
                    </CardHeader>
                    <CardContent className="space-y-4">
                        <Alert>
                            <Mail className="h-4 w-4" />
                            <AlertDescription>
                                O link de confirmação tem validade de 24 horas. Este link já expirou,
                                mas você pode solicitar um novo.
                            </AlertDescription>
                        </Alert>

                        <div className="space-y-3">
                            <Button
                                onClick={resendConfirmation}
                                className="w-full bg-terracotta hover:bg-terracotta/90"
                            >
                                Reenviar email de confirmação
                            </Button>

                            <Link href="/login">
                                <Button variant="outline" className="w-full">
                                    Ir para o login
                                </Button>
                            </Link>
                        </div>

                        <div className="text-center pt-4">
                            <p className="text-xs text-muted-foreground">
                                Problemas com a confirmação?
                                <Link href="/contact" className="text-terracotta hover:underline ml-1">
                                    Entre em contato
                                </Link>
                            </p>
                        </div>
                    </CardContent>
                </Card>
            </div>
        );
    }

    // Error state
    return (
        <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-terracotta/5 to-sage/5">
            <Card className="w-full max-w-md">
                <CardHeader className="text-center">
                    <div className="mx-auto w-12 h-12 bg-red-100 rounded-full flex items-center justify-center mb-4">
                        <XCircle className="h-6 w-6 text-red-600" />
                    </div>
                    <CardTitle className="text-2xl font-bold">Erro na Confirmação</CardTitle>
                    <CardDescription>
                        Não conseguimos confirmar seu email
                    </CardDescription>
                </CardHeader>
                <CardContent className="space-y-4">
                    <Alert variant="destructive">
                        <AlertDescription>
                            O link de confirmação é inválido ou não foi encontrado.
                            Verifique se você copiou o link completo do email.
                        </AlertDescription>
                    </Alert>

                    <div className="space-y-3">
                        <Button
                            onClick={resendConfirmation}
                            className="w-full bg-terracotta hover:bg-terracotta/90"
                        >
                            Reenviar email de confirmação
                        </Button>

                        <div className="grid grid-cols-2 gap-2">
                            <Link href="/register">
                                <Button variant="outline" className="w-full">
                                    Criar conta
                                </Button>
                            </Link>

                            <Link href="/login">
                                <Button variant="outline" className="w-full">
                                    Fazer login
                                </Button>
                            </Link>
                        </div>
                    </div>

                    <div className="text-center pt-4 border-t">
                        <p className="text-xs text-muted-foreground mb-2">
                            Precisa de ajuda?
                        </p>
                        <Link href="/contact" className="text-terracotta hover:underline text-sm">
                            Entre em contato com o suporte
                        </Link>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
};

export default EmailConfirmationPage;