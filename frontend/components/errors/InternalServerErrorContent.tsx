"use client";

import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Zap, AlertTriangle, Wrench, ArrowLeft, HomeIcon, RotateCcw } from "lucide-react";
import MainLayout from "@/components/layout/MainLayout";
import Link from "next/link";

const InternalServerErrorContent = () => {
    const [errorState, setErrorState] = useState<number>(0);
    const [sparkCount, setSparkCount] = useState<number>(0);

    useEffect(() => {
        console.error("500 Error: Erro interno do servidor");

        const interval = setInterval(() => {
            setErrorState((prev) => (prev < 2 ? prev + 1 : 0));
        }, 2000);

        const sparkInterval = setInterval(() => {
            setSparkCount((prev) => prev + 1);
        }, 300);

        return () => {
            clearInterval(interval);
            clearInterval(sparkInterval);
        };
    }, []);

    const getErrorIcon = () => {
        switch (errorState) {
            case 0:
                return <Zap className="h-20 w-20 text-yellow-500 animate-bounce" />;
            case 1:
                return <AlertTriangle className="h-20 w-20 text-orange-500 animate-pulse" />;
            default:
                return <Wrench className="h-20 w-20 text-red-500 animate-spin" />;
        }
    };

    const generateSparks = () => {
        return Array.from({ length: 5 }, (_, i) => (
            <span
                // biome-ignore lint/suspicious/noArrayIndexKey: <explanation>
                key={i}
                className={`absolute text-yellow-400 animate-ping text-xs ${i % 2 === 0 ? 'top-2 left-4' : 'bottom-2 right-4'
                    }`}
                style={{ animationDelay: `${i * 0.2}s` }}
            >
                ⚡
            </span>
        ));
    };

    return (
        <MainLayout hideFooter>
            <div className="min-h-[calc(100vh-80px)] flex flex-col items-center justify-center bg-gradient-to-b from-yellow-50 to-orange-100 dark:from-gray-900 dark:to-orange-900/20 p-4">
                <Card className="max-w-lg w-full p-6 bg-white dark:bg-gray-800 shadow-xl border-orange-200 dark:border-orange-700 relative overflow-hidden">
                    {generateSparks()}

                    <div className="flex flex-col items-center text-center">
                        <div className="mb-4 relative">
                            {getErrorIcon()}
                        </div>

                        <h1 className="text-6xl font-bold mb-2 text-orange-600 dark:text-orange-400">500</h1>
                        <h2 className="text-2xl font-semibold mb-4 text-gray-700 dark:text-gray-300">
                            Oops! Algo explodiu! 💥
                        </h2>

                        <p className="text-gray-600 dark:text-gray-400 mb-6">
                            Nosso servidor teve uma crise existencial e decidiu tirar uma folga.
                            Parece que alguém dividiu por zero e quebrou a matrix! 🤖💔
                        </p>

                        <div className="w-full mb-6 p-4 bg-yellow-50 dark:bg-yellow-900/20 rounded-lg border border-yellow-200 dark:border-yellow-700">
                            <p className="text-sm text-yellow-700 dark:text-yellow-300 mb-3">
                                🔧 Status da emergência:
                            </p>
                            <div className="bg-gray-100 dark:bg-gray-700 p-3 rounded font-mono text-xs">
                                <div className="flex items-center justify-between mb-1">
                                    <span>Sistema:</span>
                                    <span className="text-red-500 animate-pulse">PANIC!</span>
                                </div>
                                <div className="flex items-center justify-between mb-1">
                                    <span>Café do dev:</span>
                                    <span className="text-orange-500">0% ☕</span>
                                </div>
                                <div className="flex items-center justify-between">
                                    <span>Bugs encontrados:</span>
                                    <span className="text-red-500">{sparkCount} 🐛</span>
                                </div>
                            </div>
                        </div>

                        <div className="space-y-4 w-full">
                            <div className="p-4 bg-blue-50 dark:bg-blue-900/20 rounded-lg">
                                <p className="text-sm text-gray-600 dark:text-gray-300 mb-3">
                                    🚑 Equipe de resgate em ação:
                                </p>
                                <div className="text-xs text-gray-500 dark:text-gray-400 space-y-1 text-left">
                                    <div className="flex items-center">
                                        <span className="animate-pulse mr-2">⚡</span>
                                        Reiniciando hamsters que movem o servidor...
                                    </div>
                                    <div className="flex items-center">
                                        <span className="animate-bounce mr-2">🔧</span>
                                        Aplicando duct tape digital...
                                    </div>
                                    <div className="flex items-center">
                                        <span className="animate-spin mr-2">☕</span>
                                        Fazendo mais café para os devs...
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
                                    Fugir
                                </Button>
                                <Button
                                    onClick={() => window.location.reload()}
                                    variant="secondary"
                                    className="flex-1"
                                >
                                    <RotateCcw className="h-4 w-4 mr-2" />
                                    Tentar Magia
                                </Button>
                                <Link href="/" className="flex-1">
                                    <Button className="w-full bg-orange-500 hover:bg-orange-600">
                                        <HomeIcon className="h-4 w-4 mr-2" />
                                        Lugar Seguro
                                    </Button>
                                </Link>
                            </div>
                        </div>
                    </div>
                </Card>

                <div className="mt-8 text-center">
                    <p className="text-gray-600 dark:text-gray-400 text-sm">
                        Nossos engenheiros já foram notificados... e estão chorando! 😭
                    </p>
                </div>
            </div>
        </MainLayout>
    );
};

export default InternalServerErrorContent;