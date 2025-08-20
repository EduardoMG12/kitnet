'use client';

import { useState } from "react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Progress } from "@/components/ui/progress";
import { ArrowLeft, Eye, EyeOff, CheckCircle, Shield } from "lucide-react";
import { redirect, RedirectType, useSearchParams } from "next/navigation";
import Link from "next/link";

const ResetPasswordPage = () => {
    const searchParams = useSearchParams();

    const token = searchParams.get("token");

    const [passwords, setPasswords] = useState({
        new_password: "",
        confirm_password: ""
    });
    const [showPasswords, setShowPasswords] = useState({
        new_password: false,
        confirm_password: false
    });
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState("");
    const [isSuccess, setIsSuccess] = useState(false);

    // Password strength calculation
    const getPasswordStrength = (password: string) => {
        let strength = 0;
        if (password.length >= 8) strength += 20;
        if (password.match(/[a-z]/)) strength += 20;
        if (password.match(/[A-Z]/)) strength += 20;
        if (password.match(/[0-9]/)) strength += 20;
        if (password.match(/[^a-zA-Z0-9]/)) strength += 20;
        return strength;
    };

    const passwordStrength = getPasswordStrength(passwords.new_password);
    const getStrengthText = (strength: number) => {
        if (strength < 40) return "Fraca";
        if (strength < 60) return "Regular";
        if (strength < 80) return "Boa";
        return "Forte";
    };

    const getStrengthColor = (strength: number) => {
        if (strength < 40) return "bg-red-500";
        if (strength < 60) return "bg-yellow-500";
        if (strength < 80) return "bg-blue-500";
        return "bg-green-500";
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setIsLoading(true);
        setError("");

        // Validation
        if (!passwords.new_password || !passwords.confirm_password) {
            setError("Por favor, preencha todos os campos");
            setIsLoading(false);
            return;
        }

        if (passwords.new_password !== passwords.confirm_password) {
            setError("As senhas não coincidem");
            setIsLoading(false);
            return;
        }

        if (passwords.new_password.length < 8) {
            setError("A senha deve ter pelo menos 8 caracteres");
            setIsLoading(false);
            return;
        }

        if (!token) {
            setError("Token de recuperação inválido ou expirado");
            setIsLoading(false);
            return;
        }

        // Simulate API call
        setTimeout(() => {
            setIsLoading(false);
            setIsSuccess(true);

            // Redirect to login after 3 seconds
            setTimeout(() => {
                redirect("/login", RedirectType.push);
            }, 3000);
        }, 2000);
    };

    const handlePasswordChange = (field: string, value: string) => {
        setPasswords(prev => ({ ...prev, [field]: value }));
        setError("");
    };

    const togglePasswordVisibility = (field: string) => {
        setShowPasswords(prev => ({ ...prev, [field]: !prev[field as keyof typeof prev] }));
    };

    if (isSuccess) {
        return (
            <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-terracotta/5 to-sage/5">
                <Card className="w-full max-w-md">
                    <CardHeader className="text-center">
                        <div className="mx-auto w-12 h-12 bg-green-100 rounded-full flex items-center justify-center mb-4">
                            <CheckCircle className="h-6 w-6 text-green-600" />
                        </div>
                        <CardTitle className="text-2xl font-bold">Senha Alterada!</CardTitle>
                        <CardDescription>
                            Sua senha foi alterada com sucesso
                        </CardDescription>
                    </CardHeader>
                    <CardContent className="space-y-4">
                        <Alert>
                            <Shield className="h-4 w-4" />
                            <AlertDescription>
                                Sua senha foi atualizada com segurança. Você será redirecionado para a página de login em instantes.
                            </AlertDescription>
                        </Alert>

                        <Link href="/login">
                            <Button className="w-full bg-terracotta hover:bg-terracotta/90">
                                Ir para o login
                            </Button>
                        </Link>
                    </CardContent>
                </Card>
            </div>
        );
    }

    if (!token) {
        return (
            <div className="min-h-screen flex items-center justify-center p-4 bg-gradient-to-br from-terracotta/5 to-sage/5">
                <Card className="w-full max-w-md">
                    <CardHeader className="text-center">
                        <CardTitle className="text-2xl font-bold">Link Inválido</CardTitle>
                        <CardDescription>
                            Este link de recuperação é inválido ou expirou
                        </CardDescription>
                    </CardHeader>
                    <CardContent className="space-y-4">
                        <Alert variant="destructive">
                            <AlertDescription>
                                O token de recuperação não foi encontrado ou já expirou. Solicite um novo link de recuperação.
                            </AlertDescription>
                        </Alert>

                        <div className="space-y-3">
                            <Link href="/forgot-password">
                                <Button className="w-full bg-terracotta hover:bg-terracotta/90">
                                    Solicitar novo link
                                </Button>
                            </Link>

                            <Link href="/login">
                                <Button variant="outline" className="w-full">
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
                    <CardTitle className="text-2xl font-bold">Nova Senha</CardTitle>
                    <CardDescription>
                        Escolha uma senha forte para sua conta
                    </CardDescription>
                </CardHeader>
                <CardContent>
                    <form onSubmit={handleSubmit} className="space-y-4">
                        <div className="space-y-2">
                            <Label htmlFor="new_password">Nova Senha</Label>
                            <div className="relative">
                                <Input
                                    id="new_password"
                                    type={showPasswords.new_password ? "text" : "password"}
                                    placeholder="Digite sua nova senha"
                                    value={passwords.new_password}
                                    onChange={(e) => handlePasswordChange("new_password", e.target.value)}
                                    required
                                    disabled={isLoading}
                                    className="h-12 pr-10"
                                />
                                <Button
                                    type="button"
                                    variant="ghost"
                                    size="sm"
                                    className="absolute right-0 top-0 h-12 px-3 hover:bg-transparent"
                                    onClick={() => togglePasswordVisibility("new_password")}
                                >
                                    {showPasswords.new_password ? (
                                        <EyeOff className="h-4 w-4 text-muted-foreground" />
                                    ) : (
                                        <Eye className="h-4 w-4 text-muted-foreground" />
                                    )}
                                </Button>
                            </div>

                            {passwords.new_password && (
                                <div className="space-y-2">
                                    <div className="flex justify-between text-xs">
                                        <span>Força da senha:</span>
                                        <span className="font-medium">{getStrengthText(passwordStrength)}</span>
                                    </div>
                                    <Progress
                                        value={passwordStrength}
                                        className={`h-2 ${getStrengthColor(passwordStrength)}`}
                                    />
                                </div>
                            )}
                        </div>

                        <div className="space-y-2">
                            <Label htmlFor="confirm_password">Confirmar Nova Senha</Label>
                            <div className="relative">
                                <Input
                                    id="confirm_password"
                                    type={showPasswords.confirm_password ? "text" : "password"}
                                    placeholder="Digite novamente sua nova senha"
                                    value={passwords.confirm_password}
                                    onChange={(e) => handlePasswordChange("confirm_password", e.target.value)}
                                    required
                                    disabled={isLoading}
                                    className="h-12 pr-10"
                                />
                                <Button
                                    type="button"
                                    variant="ghost"
                                    size="sm"
                                    className="absolute right-0 top-0 h-12 px-3 hover:bg-transparent"
                                    onClick={() => togglePasswordVisibility("confirm_password")}
                                >
                                    {showPasswords.confirm_password ? (
                                        <EyeOff className="h-4 w-4 text-muted-foreground" />
                                    ) : (
                                        <Eye className="h-4 w-4 text-muted-foreground" />
                                    )}
                                </Button>
                            </div>

                            {passwords.confirm_password && passwords.new_password !== passwords.confirm_password && (
                                <p className="text-xs text-red-600">As senhas não coincidem</p>
                            )}
                        </div>

                        {error && (
                            <Alert variant="destructive">
                                <AlertDescription>{error}</AlertDescription>
                            </Alert>
                        )}

                        <div className="bg-muted/50 p-3 rounded-lg">
                            <p className="text-xs text-muted-foreground mb-2">Sua senha deve conter:</p>
                            <ul className="text-xs space-y-1">
                                <li className={passwords.new_password.length >= 8 ? "text-green-600" : "text-muted-foreground"}>
                                    ✓ Pelo menos 8 caracteres
                                </li>
                                <li className={passwords.new_password.match(/[A-Z]/) ? "text-green-600" : "text-muted-foreground"}>
                                    ✓ Uma letra maiúscula
                                </li>
                                <li className={passwords.new_password.match(/[a-z]/) ? "text-green-600" : "text-muted-foreground"}>
                                    ✓ Uma letra minúscula
                                </li>
                                <li className={passwords.new_password.match(/[0-9]/) ? "text-green-600" : "text-muted-foreground"}>
                                    ✓ Um número
                                </li>
                            </ul>
                        </div>

                        <Button
                            type="submit"
                            className="w-full h-12 bg-terracotta hover:bg-terracotta/90"
                            disabled={isLoading || passwords.new_password !== passwords.confirm_password || passwordStrength < 60}
                        >
                            {isLoading ? "Alterando senha..." : "Alterar senha"}
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
                </CardContent>
            </Card>
        </div>
    );
};

export default ResetPasswordPage;
