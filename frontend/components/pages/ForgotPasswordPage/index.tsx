'use client';

import { useState } from "react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { ArrowLeft, Mail, CheckCircle } from "lucide-react";
import Link from "next/link";

const ForgotPasswordPage = () => {
    const [email, setEmail] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [isSuccess, setIsSuccess] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError("");

        // Basic email validation
        if (!email || !email.includes("@")) {
            setError("Por favor, insira um email válido");
            setIsLoading(false);
            return;
        }

        // Simulate API call
        setTimeout(() => {
            setIsLoading(false);
            setIsSuccess(true);
        }, 2000);
    };

    if (isSuccess) {
        return (
            <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-terracotta/5 to-sage/5">
                <Card className="w-full max-w-md">
                    <CardHeader className="text-center">
                        <div className="mx-auto w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mb-4">
                            <CheckCircle className="h-6 w-6 text-green-600" />
                        </div>
                        <CardTitle className="text-2xl font-bold">Email Enviado!</CardTitle>
                        <CardDescription>
                            Verifique sua caixa de entrada
                        </CardDescription>
                    </CardHeader>
                    <CardContent className="space-y-4">
                        <div className="text-center space-y-2">
                            <p className="text-sm text-muted-foreground">
                                Enviamos um link de recuperação de senha para:
                            </p>
                            <p className="font-medium text-terracotta">{email}</p>
                        </div>

                        <Alert>
                            <Mail className="h-4 w-4" />
                            <AlertDescription>
                                <strong>Não encontrou o email?</strong> Verifique sua pasta de spam ou lixeira.
                                O link expira em 24 horas.
                            </AlertDescription>
                        </Alert>

                        <div className="space-y-3 pt-4">
                            <Button
                                onClick={() => {
                                    setIsSuccess(false);
                                    setEmail("");
                                }}
                                variant="outline"
                                className="w-full"
                            >
                                Enviar para outro email
                            </Button>

                            <Link href="/login">
                                <Button variant="ghost" className="w-full">
                                    <ArrowLeft className="mr-2 h-4 w-4" />
                                    Voltar ao login
                                </Button>
                            </Link>
                        </div>
                    </CardContent>
                </Card>
            </div>
        );
    }

    return (
        <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-terracotta/5 to-sage/5">
            <Card className="w-full max-w-md">
                <CardHeader className="text-center">
                    <CardTitle className="text-2xl font-bold">Esqueceu sua senha?</CardTitle>
                    <CardDescription>
                        Digite seu email para receber um link de recuperação
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div className="space-y-2">
                            <Label htmlFor="email">Email</Label>
                            <Input
                                id="email"
                                type="email"
                                placeholder="seu@email.com"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                                disabled={isLoading}
                                className="h-12"
                            />
                        </div>

                        {error && (
                            <Alert variant="destructive">
                                <AlertDescription>{error}</AlertDescription>
                            </Alert>
                        )}

                        <Button
                            type="submit"
                            className="w-full h-12 bg-terracotta hover:bg-terracotta/90"
                            disabled={isLoading}
                        >
                            {isLoading ? "Enviando..." : "Enviar link de recuperação"}
                        </Button>
                    </form>

                    <div className="mt-6 text-center">
                        <Link
                            href="/login"
                            className="inline-flex items-center text-sm text-muted-foreground hover:text-terracotta transition-colors"
                        >
                            <ArrowLeft className="mr-2 h-4 w-4" />
                            Voltar ao login
                        </Link>
                    </div>

                    <div className="mt-8 pt-6 border-t">
                        <div className="text-center space-y-2">
                            <p className="text-sm text-muted-foreground">Ainda não tem uma conta?</p>
                            <Link href="/register">
                                <Button variant="outline" className="w-full">
                                    Criar conta gratuita
                                </Button>
                            </Link>
                        </div>
                    </div>
                </CardContent>
            </Card>
        </div>
    );
};

export default ForgotPasswordPage;