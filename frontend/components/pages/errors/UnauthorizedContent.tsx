"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Lock, User, KeyRound, Eye, EyeOff, ArrowLeft, HomeIcon } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const UnauthorizedContent = () => {
    const [lockState, setLockState] = useState<number>(0);
    const [showFakePassword, setShowFakePassword] = useState<boolean>(false);
    const [attempts, setAttempts] = useState<number>(0);

    useEffect(() => {
        console.error("401 Error: Não autorizado - Authentication required");

        const stateInterval = setInterval(() => {
            setLockState((prev) => (prev < 2 ? prev + 1 : 0));
        }, 1500);

        const attemptInterval = setInterval(() => {
            setAttempts((prev) => prev + 1);
        }, 3000);

        return () => {
            clearInterval(stateInterval);
            clearInterval(attemptInterval);
        };
    }, []);

    const getLockIcon = () => {
        switch (lockState) {
            case 0:
                return <Lock className="h-20 w-20 text-yellow-500 animate-pulse" />;
            case 1:
                return <User className="h-20 w-20 text-orange-500 animate-bounce" />;
            default:
                return <KeyRound className="h-20 w-20 text-red-500 animate-spin" />;
        }
    };

    return (
        <div className="h-screen flex flex-col items-center justify-center bg-gradient-to-b from-yellow-50 to-orange-100 dark:from-gray-900 dark:to-yellow-900/20 p-4">
            <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-yellow-200 dark:border-yellow-700">
                <div className="flex flex-col items-center text-center">
                    <div className="mb-4">
                        {getLockIcon()}
                    </div>

                    <h1 className="text-6xl font-bold mb-2 text-yellow-600 dark:text-yellow-400">401</h1>
                    <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                        Quem é Você? 🕵️‍♂️
                    </h2>

                    <p className="text-gray-600 dark:text-gray-400 mb-6">
                        Eita! Parece que você não se apresentou direito na portaria.
                        O segurança digital não te reconheceu! 🚪👮‍♂️
                    </p>

                    <div className="w-full mb-6 p-4 bg-yellow-50 dark:bg-yellow-900/20 rounded-lg border border-yellow-200 dark:border-yellow-700">
                        <p className="text-sm text-yellow-700 dark:text-yellow-300 mb-3">
                            🔐 Tentativas de acesso:
                        </p>
                        <div className="bg-gray-100 dark:bg-gray-700 p-3 rounded font-mono text-xs space-y-2">
                            <div className="flex items-center justify-between">
                                <span>Usuário:</span>
                                <span className="text-gray-500">usuario_misterioso</span>
                            </div>
                            <div className="flex items-center justify-between">
                                <span>Senha:</span>
                                <div className="flex items-center gap-2">
                                    <span className="text-red-500">
                                        {showFakePassword ? "123456" : "••••••"}
                                    </span>
                                    <Button
                                        variant="ghost"
                                        size="sm"
                                        onClick={() => setShowFakePassword(!showFakePassword)}
                                        className="h-4 w-4 p-0"
                                    >
                                        {showFakePassword ? <EyeOff className="h-3 w-3" /> : <Eye className="h-3 w-3" />}
                                    </Button>
                                </div>
                            </div>
                            <div className="flex items-center justify-between">
                                <span>Tentativas:</span>
                                <span className="text-red-500">{attempts}/∞</span>
                            </div>
                            <div className="text-center text-xs text-red-500 mt-2 border-t pt-2">
                                ❌ ACESSO NEGADO - Credenciais inválidas
                            </div>
                        </div>
                    </div>

                    <div className="space-y-4 w-full">
                        <div className="p-4 bg-blue-50 dark:bg-blue-900/20 rounded-lg">
                            <p className="text-sm text-gray-600 dark:text-gray-300 mb-3">
                                🎭 Como resolver essa confusão:
                            </p>
                            <div className="text-xs text-gray-500 dark:text-gray-400 space-y-1 text-left">
                                <div className="flex items-center">
                                    <span className="mr-2">🔑</span>
                                    Faça login com suas credenciais
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">📧</span>
                                    Recupere sua senha se esqueceu
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">👤</span>
                                    Crie uma conta se não tem uma
                                </div>
                                <div className="flex items-center">
                                    <span className="mr-2">🍪</span>
                                    Ou tente subornar com cookies (não funciona)
                                </div>
                            </div>
                        </div>

                        <div className="flex flex-col sm:flex-row gap-3 justify-center">
                            <Button
                                onClick={() => window.history.back()}
                                variant="outline"
                                className="flex-1"
                            >
                                <ArrowLeft className="h-4 w-4 mr-2" />
                                Voltar
                            </Button>
                            <Link href="/login" className="flex-1">
                                <Button variant="secondary" className="w-full">
                                    <Lock className="h-4 w-4 mr-2" />
                                    Fazer Login
                                </Button>
                            </Link>
                            <Link href="/" className="flex-1">
                                <Button className="w-full bg-yellow-500 hover:bg-yellow-600">
                                    <HomeIcon className="h-4 w-4 mr-2" />
                                    Início
                                </Button>
                            </Link>
                        </div>
                    </div>
                </div>
            </Card>

            <div className="mt-8 text-center">
                <p className="text-gray-600 dark:text-gray-400 text-sm">
                    💭 Lembre-se: Sua senha não é "123456"... ou é? 🤔
                </p>
            </div>
        </div>
    );
};

export default UnauthorizedContent;